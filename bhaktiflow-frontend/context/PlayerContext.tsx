"use client";
import { createContext, useContext, useState, ReactNode } from "react";

export interface Song {
  id: string;
  title: string;
  artistName: string;
  thumbnail: string;
  videoIdOrUrl: string;
  sourceType: "YOUTUBE" | "LOCAL";
}

interface PlayerContextType {
  currentSong: Song | null;
  isPlaying: boolean;
  queue: Song[];
  setQueue: (songs: Song[]) => void;
  handlePlayPause: (song: Song) => void;
  togglePlayPause: () => void;
  playNext: () => void;
  playPrev: () => void;
}

const PlayerContext = createContext<PlayerContextType | null>(null);

export function PlayerProvider({ children }: { children: ReactNode }) {
  const [currentSong, setCurrentSong] = useState<Song | null>(null);
  const [isPlaying, setIsPlaying] = useState(false);
  const [queue, setQueue] = useState<Song[]>([]);

  const handlePlayPause = (song: Song) => {
    if (currentSong?.id === song.id) {
      setIsPlaying((prev) => !prev);
    } else {
      setCurrentSong(song);
      setIsPlaying(true);
    }
  };

  const togglePlayPause = () => setIsPlaying((prev) => !prev);

  const playNext = () => {
    if (!currentSong || queue.length === 0) return;
    const idx = queue.findIndex((s) => s.id === currentSong.id);
    const next = queue[(idx + 1) % queue.length];
    setCurrentSong(next);
    setIsPlaying(true);
  };

  const playPrev = () => {
    if (!currentSong || queue.length === 0) return;
    const idx = queue.findIndex((s) => s.id === currentSong.id);
    const prev = queue[(idx - 1 + queue.length) % queue.length];
    setCurrentSong(prev);
    setIsPlaying(true);
  };

  return (
    <PlayerContext.Provider value={{ currentSong, isPlaying, queue, setQueue, handlePlayPause, togglePlayPause, playNext, playPrev }}>
      {children}
    </PlayerContext.Provider>
  );
}

export function usePlayer() {
  const ctx = useContext(PlayerContext);
  if (!ctx) throw new Error("usePlayer must be used inside PlayerProvider");
  return ctx;
}
