"use client";
import { useState, useCallback } from "react";
import {
  fetchAllBhajans,
  fetchBhajansByMood,
  fetchBhajansByDeity,
} from "@/lib/api";
import { usePlayer, type Song } from "@/context/PlayerContext";
import dynamic from "next/dynamic";
const PeacockFeather = dynamic(() => import("@/components/PeacockFeather"), { ssr: false });

const MOODS = [
  { id: "brahma-muhurta", label: "BRAHMA MUHURTA", emoji: "🌅" },
  { id: "peaceful",       label: "PEACEFUL VIBE",  emoji: "🌿" },
  { id: "energetic",      label: "ENERGETIC VIBE", emoji: "🔥" },
  { id: "evening-aarti",  label: "EVENING AARTI",  emoji: "🌙" },
  { id: "morning-aarti",  label: "MORNING AARTI",  emoji: "🪔" },
];

const DEITIES = [
  { name: "Shiva",   image: "/lordShiva.jpeg",   shlok: "ॐ नमः शिवाय" },
  { name: "Krishna", image: "/shreeKrishna.jpeg", shlok: "राधे राधे" },
  { name: "Ram",     image: "/shreeRam.jpeg",     shlok: "जय श्री राम" },
  { name: "Hanuman", image: "/lordHanuman.jpeg",  shlok: "जय हनुमान" },
];

export default function BhajansPage() {
  const { currentSong, isPlaying, handlePlayPause, setQueue } = usePlayer();

  const [view, setView] = useState<"discovery" | "all-songs">("discovery");
  const [songs, setSongs] = useState<Song[]>([]);
  const [loading, setLoading] = useState(false);
  const [activeSource, setActiveSource] = useState("");
  const [activeMood, setActiveMood] = useState("");
  const [searchQuery, setSearchQuery] = useState("");
  const [featherTrigger, setFeatherTrigger] = useState(0);

  const triggerTransition = () => setFeatherTrigger((n) => n + 1);

  const loadSongs = useCallback(async (fetcher: () => Promise<Song[]>, label: string) => {
    triggerTransition();
    setView("all-songs");
    setActiveSource(label);
    setLoading(true);
    try {
      const data = await fetcher();
      setSongs(data);
      setQueue(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, [setQueue]);

  const goBack = () => {
    triggerTransition();
    setTimeout(() => setView("discovery"), 50);
  };

  const filteredSongs = songs.filter(
    (s) =>
      s.title?.toLowerCase().includes(searchQuery.toLowerCase()) ||
      s.artistName?.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <>
      <PeacockFeather trigger={featherTrigger} />

      <div className="w-full max-w-7xl mx-auto px-6">

        {/* ═══════════ DISCOVERY VIEW ═══════════ */}
        {view === "discovery" && (
          <div className="flex flex-col items-center page-enter">

            <h1
              className="text-6xl md:text-8xl font-black italic mb-6 text-slate-900 text-center leading-none"
              style={{ fontFamily: "'Playfair Display', serif" }}
            >
              Bhakti<span className="text-orange-500">Flow.</span>
            </h1>

            {/* Search */}
            <div className="relative w-full max-w-2xl mb-10">
              <input
                type="text"
                placeholder="Search for Bhajans, Deities, or Moods..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full bg-white/80 backdrop-blur border border-white/90 rounded-full py-4 pl-6 pr-16 text-slate-700 placeholder-slate-400 text-sm shadow-lg focus:outline-none focus:ring-2 focus:ring-orange-400"
              />
              <button
                onClick={() => searchQuery && loadSongs(fetchAllBhajans, "Search")}
                className="absolute right-2 top-1/2 -translate-y-1/2 w-10 h-10 bg-orange-500 hover:bg-orange-600 rounded-full flex items-center justify-center text-white shadow transition-colors"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round">
                  <circle cx="11" cy="11" r="8"/><path d="M21 21l-4.35-4.35"/>
                </svg>
              </button>
            </div>

            {/* Mood chips */}
            <div className="flex items-center gap-3 mb-10 flex-wrap justify-center">
              {MOODS.map((mood) => (
                <button
                  key={mood.id}
                  onClick={() => {
                    setActiveMood(mood.id);
                    loadSongs(() => fetchBhajansByMood(mood.id), mood.label);
                  }}
                  className="flex items-center gap-2 px-5 py-2.5 rounded-full text-sm font-bold border shadow-sm transition-all hover:shadow-md"
                  style={{
                    background: activeMood === mood.id ? "#f97316" : "rgba(255,255,255,0.7)",
                    color: activeMood === mood.id ? "white" : "#334155",
                    borderColor: activeMood === mood.id ? "#f97316" : "rgba(255,255,255,0.8)",
                    backdropFilter: "blur(12px)",
                  }}
                >
                  <span>{mood.emoji}</span>
                  {mood.label}
                </button>
              ))}
            </div>

            {/* Deity cards */}
            <div className="grid grid-cols-2 lg:grid-cols-4 gap-6 w-full">
              {DEITIES.map((deity) => (
                <button
                  key={deity.name}
                  onClick={() => loadSongs(() => fetchBhajansByDeity(deity.name), deity.name)}
                  className="song-card group relative aspect-[3/4] rounded-[24px] overflow-hidden shadow-xl text-left"
                >
                  <img
                    src={deity.image}
                    alt={deity.name}
                    className="absolute inset-0 w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent"/>
                  <div className="absolute bottom-5 left-5">
                    <h3
                      className="text-3xl font-black text-white italic"
                      style={{ fontFamily: "'Playfair Display', serif" }}
                    >
                      {deity.name}
                    </h3>
                    <p className="text-xs text-orange-300 mt-1 font-medium">{deity.shlok}</p>
                  </div>
                  <div className="absolute bottom-0 left-0 h-1 bg-orange-500 w-0 group-hover:w-full transition-all duration-500"/>
                </button>
              ))}
            </div>
          </div>
        )}

        {/* ═══════════ ALL SONGS VIEW ═══════════ */}
        {view === "all-songs" && (
          <div className="page-enter">
            <button
              onClick={goBack}
              className="flex items-center gap-2 text-orange-500 font-bold mb-8 hover:gap-3 transition-all"
            >
              ← Back to Discovery
            </button>

            <h1
              className="text-5xl md:text-7xl font-black italic mb-6 text-slate-900 leading-none"
              style={{ fontFamily: "'Playfair Display', serif" }}
            >
              {activeSource} <span className="text-orange-500">Bhajans.</span>
            </h1>

            {/* Filter within results */}
            <div className="relative w-full max-w-md mb-8">
              <input
                type="text"
                placeholder="Filter results..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full bg-white/80 backdrop-blur border border-white/90 rounded-full py-3 pl-5 pr-12 text-slate-700 placeholder-slate-400 text-sm shadow focus:outline-none focus:ring-2 focus:ring-orange-400"
              />
            </div>

            {loading ? (
              <div className="flex items-center justify-center py-20">
                <div className="flex gap-2">
                  {[0, 1, 2].map((i) => (
                    <div
                      key={i}
                      className="w-3 h-3 bg-orange-500 rounded-full animate-bounce"
                      style={{ animationDelay: `${i * 0.15}s` }}
                    />
                  ))}
                </div>
              </div>
            ) : (
              <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
                {filteredSongs.map((song) => {
                  const isCurrentPlaying = currentSong?.id === song.id && isPlaying;
                  return (
                    <div key={song.id} className="song-card group relative rounded-[20px] overflow-hidden shadow-lg bg-white aspect-[3/4]">
                      <img
                        src={song.thumbnail || "/default-bhajan.jpg"}
                        alt={song.title}
                        className="absolute inset-0 w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                      />
                      <div className="absolute inset-0 bg-gradient-to-t from-black/85 via-black/30 to-transparent"/>

                      {isCurrentPlaying && (
                        <div className="absolute top-3 right-3 flex gap-0.5 items-end h-4">
                          {[1, 2, 3].map((b) => (
                            <div
                              key={b}
                              className="w-1 bg-orange-400 rounded-full animate-bounce"
                              style={{ height: `${b * 4 + 4}px`, animationDelay: `${b * 0.1}s` }}
                            />
                          ))}
                        </div>
                      )}

                      <div className="absolute bottom-0 left-0 right-0 p-3">
                        <h3 className="text-xs font-bold text-white line-clamp-2 leading-tight mb-0.5">
                          {song.title}
                        </h3>
                        <p className="text-[9px] text-orange-300 uppercase font-semibold truncate mb-2">
                          {song.artistName}
                        </p>
                        <button
                          onClick={() => handlePlayPause(song)}
                          className="w-full py-2 rounded-xl text-xs font-black uppercase tracking-wide transition-all"
                          style={{
                            background: isCurrentPlaying ? "white" : "#f97316",
                            color: isCurrentPlaying ? "#f97316" : "white",
                          }}
                        >
                          {isCurrentPlaying ? "⏸ PAUSE" : "▶ PLAY"}
                        </button>
                      </div>
                    </div>
                  );
                })}

                {filteredSongs.length === 0 && !loading && (
                  <div className="col-span-5 text-center py-16 text-slate-400 font-medium">
                    No bhajans found. Try a different search.
                  </div>
                )}
              </div>
            )}
          </div>
        )}
      </div>
    </>
  );
}
