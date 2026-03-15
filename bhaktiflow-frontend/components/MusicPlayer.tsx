"use client";
import { useState, useEffect, useRef, useCallback } from "react";
import { usePlayer } from "@/context/PlayerContext";

const PlayIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor"><polygon points="6,3 20,12 6,21"/></svg>
);
const PauseIcon = () => (
  <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
    <rect x="5" y="3" width="4" height="18" rx="1"/><rect x="15" y="3" width="4" height="18" rx="1"/>
  </svg>
);
const NextIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor">
    <polygon points="5,3 15,12 5,21"/><rect x="17" y="3" width="3" height="18" rx="1"/>
  </svg>
);
const PrevIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="currentColor">
    <polygon points="19,3 9,12 19,21"/><rect x="4" y="3" width="3" height="18" rx="1"/>
  </svg>
);
const ShuffleIcon = () => (
  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <polyline points="16,3 21,3 21,8"/><polyline points="16,21 21,21 21,16"/>
    <path d="M4,4l17,17"/><path d="M4,20l5-5"/><path d="M15,9l6-6"/>
  </svg>
);
const RepeatIcon = () => (
  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <polyline points="17,1 21,5 17,9"/><path d="M3,11V9a4,4,0,0,1,4-4h14"/>
    <polyline points="7,23 3,19 7,15"/><path d="M21,13v2a4,4,0,0,1-4,4H3"/>
  </svg>
);
const LyricsIcon = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
    <polyline points="14,2 14,8 20,8"/>
    <line x1="16" y1="13" x2="8" y2="13"/>
    <line x1="16" y1="17" x2="8" y2="17"/>
    <polyline points="10,9 9,9 8,9"/>
  </svg>
);

function formatTime(secs: number) {
  if (!secs || isNaN(secs) || secs < 0) return "0:00";
  const m = Math.floor(secs / 60);
  const s = Math.floor(secs % 60);
  return `${m}:${s.toString().padStart(2, "0")}`;
}

export default function MusicPlayer() {
  const { currentSong, isPlaying, togglePlayPause, playNext, playPrev } = usePlayer();

  const [isLiked, setIsLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(1240);
  const [iframeKey, setIframeKey] = useState(0);
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const [showLyrics, setShowLyrics] = useState(false);
  const [isSeeking, setIsSeeking] = useState(false);

  const iframeRef = useRef<HTMLIFrameElement>(null);
  const prevSongId = useRef<string | null>(null);
  const prevIsPlaying = useRef<boolean>(false);
  const pollRef = useRef<NodeJS.Timeout | null>(null);

  // ── Remount iframe when song or play state changes ──
  useEffect(() => {
    const songChanged = currentSong?.id !== prevSongId.current;
    const playStateChanged = isPlaying !== prevIsPlaying.current;
    if (songChanged || playStateChanged) {
      prevSongId.current = currentSong?.id ?? null;
      prevIsPlaying.current = isPlaying;
      setIframeKey((k) => k + 1);
      setCurrentTime(0);
      setDuration(0);
    }
  }, [currentSong?.id, isPlaying]);

  // ── Listen to YouTube iframe API messages for time updates ──
  useEffect(() => {
    const handleMessage = (event: MessageEvent) => {
      if (!event.data || typeof event.data !== "string") return;
      try {
        const data = JSON.parse(event.data);
        // YouTube sends info events with currentTime and duration
        if (data.event === "infoDelivery" && data.info) {
          if (data.info.currentTime !== undefined) {
            if (!isSeeking) setCurrentTime(data.info.currentTime);
          }
          if (data.info.duration !== undefined && data.info.duration > 0) {
            setDuration(data.info.duration);
          }
        }
        // Also handle onStateChange to detect ended
        if (data.event === "onStateChange" && data.info === 0) {
          // State 0 = ended
          playNext();
        }
      } catch {
        // not a JSON message, ignore
      }
    };

    window.addEventListener("message", handleMessage);
    return () => window.removeEventListener("message", handleMessage);
  }, [isSeeking, playNext]);

  // ── Poll YouTube iframe for currentTime every second ──
  // YouTube's postMessage API requires polling for time updates
  useEffect(() => {
    if (pollRef.current) clearInterval(pollRef.current);

    if (isPlaying && iframeRef.current) {
      pollRef.current = setInterval(() => {
        try {
          iframeRef.current?.contentWindow?.postMessage(
            JSON.stringify({ event: "listening", id: 1 }),
            "*"
          );
          // Request current time from YouTube player
          iframeRef.current?.contentWindow?.postMessage(
            JSON.stringify({ event: "command", func: "getCurrentTime", args: [] }),
            "*"
          );
        } catch { /* iframe not ready yet */ }
      }, 1000);
    }

    return () => {
      if (pollRef.current) clearInterval(pollRef.current);
    };
  }, [isPlaying, iframeKey]);

  const handleSeekStart = () => setIsSeeking(true);

  const handleSeekChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentTime(Number(e.target.value));
  };

  const handleSeekEnd = (e: React.ChangeEvent<HTMLInputElement>) => {
    const val = Number(e.target.value);
    setCurrentTime(val);
    setIsSeeking(false);
    // Seek the YouTube iframe via postMessage
    try {
      iframeRef.current?.contentWindow?.postMessage(
        JSON.stringify({ event: "command", func: "seekTo", args: [val, true] }),
        "*"
      );
    } catch { /* ignore */ }
  };

  const toggleLike = () => {
    setLikeCount((p) => (isLiked ? p - 1 : p + 1));
    setIsLiked((p) => !p);
  };

  const progressPercent = duration > 0 ? (currentTime / duration) * 100 : 0;

  const embedUrl = currentSong?.sourceType === "YOUTUBE" && currentSong?.videoIdOrUrl
    ? `https://www.youtube.com/embed/${currentSong.videoIdOrUrl}?autoplay=${isPlaying ? 1 : 0}&enablejsapi=1&modestbranding=1&rel=0&origin=${typeof window !== "undefined" ? window.location.origin : ""}`
    : null;

  return (
    <>
      {/* ── Lyrics Panel (slides up from bottom above player) ── */}
      {showLyrics && (
        <div
          className="fixed bottom-[88px] left-1/2 -translate-x-1/2 w-[90%] max-w-2xl z-[99] rounded-[24px] p-6 shadow-2xl"
          style={{
            background: "rgba(255,255,255,0.92)",
            backdropFilter: "blur(24px)",
            border: "1px solid rgba(255,255,255,0.9)",
            maxHeight: "50vh",
            overflowY: "auto",
          }}
        >
          <div className="flex items-center justify-between mb-4">
            <div>
              <h3 className="font-black text-slate-900 text-lg">
                {currentSong?.title ?? "No song selected"}
              </h3>
              <p className="text-xs text-orange-500 font-semibold mt-0.5">
                {currentSong?.artistName ?? ""}
              </p>
            </div>
            <button
              onClick={() => setShowLyrics(false)}
              className="w-8 h-8 rounded-full bg-slate-100 flex items-center justify-center text-slate-500 hover:bg-slate-200 transition-colors text-lg"
            >
              ×
            </button>
          </div>

          {currentSong ? (
            <div className="text-slate-600 text-sm leading-relaxed space-y-1">
              <p className="text-slate-400 italic text-xs text-center py-6">
                🎵 Lyrics for this bhajan will appear here.<br/>
                Connect your lyrics API or add them to your database.
              </p>
            </div>
          ) : (
            <p className="text-slate-400 text-sm text-center py-6">
              Select a song to view lyrics.
            </p>
          )}
        </div>
      )}

      {/* ── Main Player Bar ── */}
      <div className="fixed bottom-0 left-0 w-full z-[100] px-5 pb-5">
        <div
          className="max-w-7xl mx-auto rounded-[28px] px-6 py-3 shadow-2xl flex items-center gap-6"
          style={{
            background: "rgba(255,255,255,0.82)",
            backdropFilter: "blur(24px)",
            WebkitBackdropFilter: "blur(24px)",
            border: "1px solid rgba(255,255,255,0.92)",
          }}
        >
          {/* ── LEFT: Thumbnail + Info + Like ── */}
          <div className="flex items-center gap-3 w-[260px] flex-shrink-0">
            <div className="relative w-12 h-12 flex-shrink-0">
              <div
                className="w-12 h-12 rounded-xl overflow-hidden shadow-md"
                style={{ animation: isPlaying ? "spin 4s linear infinite" : "none" }}
              >
                {currentSong?.thumbnail ? (
                  <img src={currentSong.thumbnail} alt="" className="w-full h-full object-cover"/>
                ) : (
                  <div className="w-full h-full bg-gradient-to-br from-orange-200 to-rose-200"/>
                )}
              </div>
              <div className="absolute inset-0 flex items-center justify-center pointer-events-none">
                <div className="w-3 h-3 bg-white rounded-full shadow border border-slate-200"/>
              </div>
            </div>

            <div className="min-w-0 flex-1">
              <p className="text-[13px] font-bold text-slate-900 truncate leading-tight">
                {currentSong?.title ?? "No song selected"}
              </p>
              <p className="text-[10px] text-slate-500 truncate mt-0.5">
                {currentSong?.artistName ?? "—"}
              </p>
            </div>

            <button
              onClick={toggleLike}
              className="flex items-center gap-1 px-2.5 py-1.5 rounded-full flex-shrink-0 transition-all"
              style={{ background: "rgba(241,245,249,0.6)", border: "1px solid rgba(226,232,240,0.6)" }}
            >
              <svg width="14" height="14" viewBox="0 0 24 24"
                fill={isLiked ? "#f43f5e" : "none"}
                stroke={isLiked ? "#f43f5e" : "#94a3b8"} strokeWidth="2"
              >
                <path d="M20.84,4.61a5.5,5.5,0,0,0-7.78,0L12,5.67,10.94,4.61a5.5,5.5,0,0,0-7.78,7.78l1.06,1.06L12,21.23l7.78-7.78,1.06-1.06A5.5,5.5,0,0,0,20.84,4.61Z"/>
              </svg>
              <span className="text-[11px] font-bold" style={{ color: isLiked ? "#f43f5e" : "#64748b" }}>
                {likeCount.toLocaleString()}
              </span>
            </button>
          </div>

          {/* ── CENTER: Hidden iframe + controls + seek ── */}
          <div className="flex-1 flex items-center gap-5">
            {/* YouTube iframe — visibility:hidden keeps audio alive */}
            <div className="relative flex-shrink-0" style={{ width: 1, height: 1, overflow: "hidden" }}>
              {embedUrl && (
                <iframe
                  ref={iframeRef}
                  key={iframeKey}
                  src={embedUrl}
                  width="1"
                  height="1"
                  allow="autoplay; encrypted-media"
                  style={{ border: "none", visibility: "hidden", position: "absolute" }}
                  title={currentSong?.title ?? "player"}
                />
              )}
            </div>

            {/* Controls + seek stacked */}
            <div className="flex flex-col gap-2 flex-1">
              {/* Buttons */}
              <div className="flex items-center justify-center gap-6">
                <button className="text-slate-400 hover:text-orange-500 transition-colors">
                  <ShuffleIcon/>
                </button>
                <button onClick={playPrev} disabled={!currentSong}
                  className="text-slate-600 hover:text-orange-500 transition-colors disabled:opacity-30">
                  <PrevIcon/>
                </button>

                <button
                  onClick={togglePlayPause}
                  disabled={!currentSong}
                  className="w-11 h-11 rounded-full flex items-center justify-center text-white shadow-lg transition-all disabled:opacity-40 disabled:cursor-not-allowed hover:scale-105 active:scale-95"
                  style={{
                    background: "#f97316",
                    boxShadow: isPlaying ? "0 0 16px rgba(249,115,22,0.5)" : undefined,
                  }}
                >
                  {isPlaying ? <PauseIcon/> : <PlayIcon/>}
                </button>

                <button onClick={playNext} disabled={!currentSong}
                  className="text-slate-600 hover:text-orange-500 transition-colors disabled:opacity-30">
                  <NextIcon/>
                </button>
                <button className="text-slate-400 hover:text-orange-500 transition-colors">
                  <RepeatIcon/>
                </button>
              </div>

              {/* ✅ Seek bar — wired to real YouTube time via postMessage API */}
              <div className="flex items-center gap-3">
                <span className="text-[10px] font-semibold text-slate-400 w-8 text-right tabular-nums">
                  {formatTime(currentTime)}
                </span>

                <div className="flex-1 relative h-4 flex items-center">
                  {/* Track background */}
                  <div className="absolute w-full h-1.5 rounded-full overflow-hidden" style={{ background: "#e2e8f0" }}>
                    <div
                      className="h-full rounded-full transition-all duration-300"
                      style={{ width: `${progressPercent}%`, background: "#f97316" }}
                    />
                  </div>
                  {/* Range input on top */}
                  <input
                    type="range"
                    min={0}
                    max={duration || 100}
                    step={0.5}
                    value={currentTime}
                    onMouseDown={handleSeekStart}
                    onTouchStart={handleSeekStart}
                    onChange={handleSeekChange}
                    onPointerUp={(e) => handleSeekEnd(e as any)}
                    className="absolute w-full opacity-0 cursor-pointer h-4"
                    style={{ zIndex: 2 }}
                  />
                </div>

                <span className="text-[10px] font-semibold text-slate-400 w-8 tabular-nums">
                  {formatTime(duration)}
                </span>
              </div>
            </div>
          </div>

          {/* ── RIGHT: Lyrics button ── */}
          <div className="flex items-center justify-end w-[120px] flex-shrink-0">
            <button
              onClick={() => setShowLyrics((p) => !p)}
              className="flex items-center gap-2 px-4 py-2 rounded-full text-xs font-bold transition-all"
              style={{
                background: showLyrics ? "#f97316" : "rgba(241,245,249,0.8)",
                color: showLyrics ? "white" : "#64748b",
                border: `1px solid ${showLyrics ? "#f97316" : "rgba(226,232,240,0.8)"}`,
              }}
            >
              <LyricsIcon/>
              Lyrics
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
