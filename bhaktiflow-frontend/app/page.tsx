"use client";
import Link from "next/link";
import dynamic from "next/dynamic";

const PeacockFeather = dynamic(() => import("@/components/PeacockFeather"), { ssr: false });
const TodaySignificance = dynamic(() => import("@/components/TodaySignificance"), { ssr: false });

const FEATURES = [
  { emoji: "🎵", label: "Bhajans",   desc: "Stream sacred songs",   href: "/bhajans",   color: "#f97316" },
  { emoji: "📜", label: "Poems",     desc: "Devotional verses",     href: "/poems",     color: "#8b5cf6" },
  { emoji: "📖", label: "Kathas",    desc: "Divine stories",        href: "/kathas",    color: "#ef4444" },
  { emoji: "🎨", label: "Paintings", desc: "Sacred art",            href: "/paintings", color: "#06b6d4" },
  { emoji: "📚", label: "Books",     desc: "Holy scriptures",       href: "/books",     color: "#22c55e" },
];

export default function LandingPage() {
  return (
    <>
      <PeacockFeather trigger={1} />

      <div className="w-full max-w-6xl mx-auto px-6 flex flex-col items-center text-center">

        {/* Badge */}
        <p className="text-xs font-black uppercase tracking-[0.3em] text-orange-500 mb-6">
          ✦ Sacred Frequencies ✦
        </p>

        {/* Hero title */}
        <h1
          className="font-black italic text-slate-900 leading-none mb-6"
          style={{
            fontFamily: "var(--font-playfair), 'Georgia', serif",
            fontSize: "clamp(4rem, 12vw, 9rem)",
          }}
        >
          Bhakti<span style={{ color: "#f97316" }}>Flow.</span>
        </h1>

        <p
          className="text-lg text-slate-500 font-medium max-w-lg mx-auto leading-relaxed mb-10"
          style={{ fontFamily: "var(--font-dm-sans), sans-serif" }}
        >
          Your daily spiritual companion — stream bhajans, read sacred texts,
          explore divine art, and deepen your devotion.
        </p>

        {/* CTA buttons */}
        <div className="flex items-center gap-4 justify-center mb-14 flex-wrap">
          <Link
            href="/bhajans"
            className="flex items-center gap-2 px-8 py-4 rounded-full text-white font-black text-sm shadow-xl hover:shadow-2xl transition-all hover:scale-105 active:scale-95"
            style={{ background: "linear-gradient(135deg, #f97316, #ea580c)" }}
          >
            🎵 Start Listening
          </Link>
          <Link
            href="/books"
            className="flex items-center gap-2 px-8 py-4 rounded-full font-black text-sm transition-all hover:scale-105 active:scale-95 text-slate-800"
            style={{
              background: "rgba(255,255,255,0.80)",
              backdropFilter: "blur(12px)",
              border: "1px solid rgba(255,255,255,0.95)",
              boxShadow: "0 4px 20px rgba(0,0,0,0.08)",
            }}
          >
            📚 Read Scriptures
          </Link>
        </div>

        {/* ✅ TODAY'S SIGNIFICANCE BOX */}
        <TodaySignificance />

        {/* Feature cards */}
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4 w-full mb-12 mt-6">
          {FEATURES.map((f) => (
            <Link
              key={f.href}
              href={f.href}
              className="group flex flex-col items-center gap-3 p-6 rounded-[24px] transition-all duration-300 hover:-translate-y-2 hover:shadow-2xl"
              style={{
                background: "rgba(255,255,255,0.75)",
                backdropFilter: "blur(16px)",
                border: `1px solid ${f.color}25`,
                boxShadow: "0 4px 20px rgba(0,0,0,0.06)",
              }}
            >
              <div
                className="w-16 h-16 rounded-2xl flex items-center justify-center transition-transform group-hover:scale-110 duration-300 shadow-sm"
                style={{ background: `${f.color}15`, fontSize: "2rem" }}
              >
                {f.emoji}
              </div>
              <div>
                <p className="font-black text-slate-900 text-base">{f.label}</p>
                <p className="text-[11px] text-slate-400 font-medium mt-0.5">{f.desc}</p>
              </div>
              <div
                className="h-1 rounded-full transition-all duration-300 group-hover:w-12"
                style={{ background: f.color, width: "2rem" }}
              />
            </Link>
          ))}
        </div>

        {/* Sanskrit quote */}
        <div
          className="px-10 py-7 rounded-[28px] max-w-2xl w-full mb-4"
          style={{
            background: "rgba(255,255,255,0.65)",
            backdropFilter: "blur(20px)",
            border: "1px solid rgba(255,255,255,0.90)",
            boxShadow: "0 8px 40px rgba(249,115,22,0.08)",
          }}
        >
          <p
            className="text-3xl font-black italic text-slate-800 leading-snug"
            style={{ fontFamily: "var(--font-playfair), 'Georgia', serif" }}
          >
            "भक्ति से बड़ा कोई धर्म नहीं"
          </p>
          <p className="text-xs font-bold mt-3 uppercase tracking-widest" style={{ color: "#f97316" }}>
            — There is no religion greater than devotion
          </p>
        </div>

      </div>
    </>
  );
}
