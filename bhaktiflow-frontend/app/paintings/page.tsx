"use client";
import { useState } from "react";

const PAINTINGS = [
  { title: "Krishna with Flute", artist: "Raja Ravi Varma", deity: "Krishna", style: "Classic", thumb: "/shreeKrishna.jpeg" },
  { title: "Shiva in Meditation", artist: "Unknown", deity: "Shiva", style: "Modern", thumb: "/lordShiva.jpeg" },
  { title: "Ram Darbar", artist: "Traditional", deity: "Ram", style: "Tanjore", thumb: "/shreeRam.jpeg" },
  { title: "Hanuman with Gada", artist: "Folk Art", deity: "Hanuman", style: "Folk", thumb: "/lordHanuman.jpeg" },
  { title: "Radha Krishna", artist: "Pichwai Art", deity: "Krishna", style: "Pichwai", thumb: "/shreeKrishna.jpeg" },
  { title: "Nataraja", artist: "Chola Style", deity: "Shiva", style: "Classical", thumb: "/lordShiva.jpeg" },
  { title: "Sita Ram", artist: "Madhubani", deity: "Ram", style: "Madhubani", thumb: "/shreeRam.jpeg" },
  { title: "Bajrang Bali", artist: "Folk", deity: "Hanuman", style: "Folk", thumb: "/lordHanuman.jpeg" },
];

const STYLES = ["All", "Classic", "Modern", "Tanjore", "Folk", "Pichwai", "Madhubani", "Classical"];

export default function PaintingsPage() {
  const [activeStyle, setActiveStyle] = useState("All");
  const [selected, setSelected] = useState<typeof PAINTINGS[0] | null>(null);

  const filtered = activeStyle === "All" ? PAINTINGS : PAINTINGS.filter(p => p.style === activeStyle);

  return (
    <div className="w-full max-w-7xl mx-auto px-6">
      <div className="flex items-center gap-3 mb-2">
        <span className="text-5xl">🎨</span>
        <div>
          <h1 className="text-5xl md:text-7xl font-black italic text-slate-900 leading-none"
            style={{ fontFamily: "'Playfair Display', serif" }}>
            Sacred <span className="text-cyan-500">Paintings.</span>
          </h1>
          <p className="text-slate-500 font-medium mt-1">Divine art &amp; spiritual imagery</p>
        </div>
      </div>

      {/* Style filter chips */}
      <div className="flex gap-2 flex-wrap mt-8 mb-8">
        {STYLES.map(style => (
          <button key={style} onClick={() => setActiveStyle(style)}
            className="px-4 py-2 rounded-full text-xs font-bold transition-all"
            style={{
              background: activeStyle === style ? "#06b6d4" : "rgba(255,255,255,0.7)",
              color: activeStyle === style ? "white" : "#64748b",
              border: `1px solid ${activeStyle === style ? "#06b6d4" : "rgba(226,232,240,0.8)"}`,
            }}>
            {style}
          </button>
        ))}
      </div>

      {/* Masonry-style grid */}
      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {filtered.map((p, i) => (
          <div key={i} onClick={() => setSelected(p)}
            className="group relative rounded-[20px] overflow-hidden cursor-pointer transition-all duration-300 hover:-translate-y-1 hover:shadow-2xl"
            style={{ aspectRatio: i % 3 === 0 ? "3/4" : "1/1" }}>
            <img src={p.thumb} alt={p.title} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"/>
            <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity"/>
            <div className="absolute bottom-3 left-3 right-3 translate-y-4 group-hover:translate-y-0 opacity-0 group-hover:opacity-100 transition-all duration-300">
              <p className="text-white font-bold text-sm line-clamp-1">{p.title}</p>
              <p className="text-cyan-300 text-[10px] font-medium">{p.style}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Lightbox */}
      {selected && (
        <div className="fixed inset-0 z-[300] flex items-center justify-center p-6"
          style={{ background: "rgba(0,0,0,0.85)" }}
          onClick={() => setSelected(null)}>
          <div className="relative max-w-2xl w-full rounded-[28px] overflow-hidden shadow-2xl"
            onClick={e => e.stopPropagation()}>
            <img src={selected.thumb} alt={selected.title} className="w-full object-cover max-h-[70vh]"/>
            <div className="p-5" style={{ background: "rgba(255,255,255,0.95)" }}>
              <h3 className="font-black text-slate-900 text-xl">{selected.title}</h3>
              <p className="text-slate-500 text-sm mt-1">{selected.artist}</p>
              <div className="flex gap-2 mt-3">
                <span className="text-[10px] font-bold px-2.5 py-1 rounded-full" style={{ background: "rgba(6,182,212,0.1)", color: "#06b6d4" }}>{selected.style}</span>
                <span className="text-[10px] font-bold px-2.5 py-1 rounded-full" style={{ background: "rgba(249,115,22,0.1)", color: "#f97316" }}>{selected.deity}</span>
              </div>
            </div>
            <button onClick={() => setSelected(null)}
              className="absolute top-3 right-3 w-8 h-8 rounded-full bg-black/50 text-white flex items-center justify-center text-lg hover:bg-black/70 transition-colors">
              ×
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
