"use client";
import { HardDrive, Youtube, Library } from "lucide-react";
import { useState, useEffect } from "react";

export default function SourceSelector({ onSelectSource }: { onSelectSource: (id: string) => void }) {
  const [mounted, setMounted] = useState(false);
  const [source, setSource] = useState<string | null>(null);

  useEffect(() => { setMounted(true); }, []);
  if (!mounted) return null;

  const handleSourceClick = (id: string) => {
    setSource(id);
    onSelectSource(id); // Trigger the API fetch and screen swap in Home
  };

  return (
    <div className="flex gap-4 mb-8 overflow-x-auto pb-2 no-scrollbar">
      <button onClick={() => handleSourceClick("all")} className={`flex items-center gap-3 px-8 py-4 rounded-[24px] transition-all ${source === "all" ? "bg-slate-900 text-white" : "bg-white/50 text-slate-600"}`}>
        <Library size={20} /> <span className="font-bold uppercase tracking-tighter">All Bhajans</span>
      </button>

      <button onClick={() => handleSourceClick("local")} className={`flex items-center gap-3 px-8 py-4 rounded-[24px] transition-all ${source === "local" ? "bg-slate-900 text-white" : "bg-white/50 text-slate-600"}`}>
        <HardDrive size={20} /> <span className="font-bold uppercase tracking-tighter">Local Library</span>
      </button>

      <button onClick={() => handleSourceClick("youtube")} className={`flex items-center gap-3 px-8 py-4 rounded-[24px] transition-all ${source === "youtube" ? "bg-slate-900 text-white shadow-xl" : "bg-white/50 text-slate-600 hover:bg-white/80"}`}>
        <Youtube size={20} className={source === "youtube" ? "text-red-500" : ""} /> <span className="font-bold uppercase tracking-tighter">YouTube Music</span>
      </button>
    </div>
  );
}