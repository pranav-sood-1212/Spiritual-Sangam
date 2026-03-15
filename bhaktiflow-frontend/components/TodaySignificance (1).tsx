"use client";
import { useState, useEffect } from "react";

interface DaySignificance {
  title: string;
  description: string;
  deity: string;
  type: string;
  videoId: string;
  gradient: string;
  accentColor: string;
  emoji: string;
}

const SIGNIFICANCE_MAP: Record<string, DaySignificance> = {
  "03-15": {
    title: "Phalguna Purnima — Holi Eve",
    description: "Today is Phalguna Purnima, the full moon night marking the eve of Holi. It is the night of Holika Dahan — the bonfire symbolizing the victory of devotion over evil. Legend says Prahlad's devotion to Lord Vishnu saved him from the fire lit by Holika. This sacred night calls for prayer, singing bhajans, and remembering the power of true Bhakti.",
    deity: "Vishnu / Prahlad",
    type: "Purnima",
    videoId: "dQw4w9WgXcQ",
    gradient: "linear-gradient(135deg, #7c2d12 0%, #c2410c 40%, #f97316 100%)",
    accentColor: "#fb923c",
    emoji: "🔥",
  },
  "01-14": {
    title: "Makar Sankranti",
    description: "The Sun enters Capricorn today — a deeply auspicious transition marking the end of winter solstice. This is a day of gratitude to the Sun God, Surya. Devotees take holy dips, offer sesame-jaggery, and fly kites as a symbol of freedom and joy.",
    deity: "Surya Dev",
    type: "Festival",
    videoId: "dQw4w9WgXcQ",
    gradient: "linear-gradient(135deg, #78350f 0%, #d97706 40%, #fbbf24 100%)",
    accentColor: "#fcd34d",
    emoji: "☀️",
  },
};

function getDefaultSignificance(date: Date): DaySignificance {
  const day = date.getDay();
  const weekDays: DaySignificance[] = [
    {
      title: "Ravivar — Day of Surya Dev",
      description: "Sunday is dedicated to Lord Surya, the Sun God. Devotees offer water to the rising sun and recite the Aditya Hridayam. The Sun is the visible form of the Divine — the source of all light, life, and energy on Earth.",
      deity: "Surya Dev", type: "Weekly", videoId: "dQw4w9WgXcQ",
      gradient: "linear-gradient(135deg, #78350f 0%, #b45309 40%, #f59e0b 100%)",
      accentColor: "#fcd34d", emoji: "☀️",
    },
    {
      title: "Somvar — Sacred Day of Shiva",
      description: "Monday (Somvar) is the most auspicious day for Lord Shiva worship. Devotees offer milk, bel patra, and water to the Shivling, chanting 'Om Namah Shivaya' to seek blessings of peace and liberation.",
      deity: "Lord Shiva", type: "Weekly", videoId: "dQw4w9WgXcQ",
      gradient: "linear-gradient(135deg, #0c4a6e 0%, #0369a1 40%, #38bdf8 100%)",
      accentColor: "#7dd3fc", emoji: "🕉️",
    },
    {
      title: "Mangalvar — Day of Hanuman",
      description: "Tuesday is dedicated to Lord Hanuman. Worshipping Hanuman removes all obstacles and protects from negative energies. Reciting the Hanuman Chalisa brings courage, strength, and devotion.",
      deity: "Lord Hanuman", type: "Weekly", videoId: "dQw4w9WgXcQ",
      gradient: "linear-gradient(135deg, #7f1d1d 0%, #b91c1c 40%, #f87171 100%)",
      accentColor: "#fca5a5", emoji: "🙏",
    },
    {
      title: "Budhvar — Day of Lord Vishnu",
      description: "Wednesday is associated with Lord Vishnu and Budh Dev. Devotees recite Vishnu Sahasranama and offer tulsi leaves, praying for wisdom, intelligence, and prosperity.",
      deity: "Lord Vishnu", type: "Weekly", videoId: "dQw4w9WgXcQ",
      gradient: "linear-gradient(135deg, #14532d 0%, #15803d 40%, #4ade80 100%)",
      accentColor: "#86efac", emoji: "🌿",
    },
    {
      title: "Guruvar — Day of Brihaspati",
      description: "Thursday is sacred to Guru Brihaspati and Lord Vishnu. Devotees wear yellow, offer yellow flowers, and seek blessings of knowledge and prosperity. The best day for new beginnings.",
      deity: "Lord Vishnu / Sai Baba", type: "Weekly", videoId: "dQw4w9WgXcQ",
      gradient: "linear-gradient(135deg, #713f12 0%, #a16207 40%, #facc15 100%)",
      accentColor: "#fde68a", emoji: "💛",
    },
    {
      title: "Shukravar — Day of Maa Lakshmi",
      description: "Friday is the most auspicious day for Goddess Lakshmi worship. Devotees clean their homes, light diyas, and offer white flowers. Reciting the Shri Sukta brings wealth and happiness.",
      deity: "Maa Lakshmi", type: "Weekly", videoId: "dQw4w9WgXcQ",
      gradient: "linear-gradient(135deg, #831843 0%, #be185d 40%, #f472b6 100%)",
      accentColor: "#fbcfe8", emoji: "🌸",
    },
    {
      title: "Shanivar — Day of Shani Dev",
      description: "Saturday is dedicated to Shani Dev, the lord of justice and karma. Devotees offer sesame oil and light a lamp under the Peepal tree. Visiting Hanuman temples on Saturday helps overcome Saturn's challenges.",
      deity: "Shani Dev", type: "Weekly", videoId: "dQw4w9WgXcQ",
      gradient: "linear-gradient(135deg, #1c1917 0%, #44403c 40%, #78716c 100%)",
      accentColor: "#d6d3d1", emoji: "⚖️",
    },
  ];
  return weekDays[day];
}

function getSignificanceForToday(): DaySignificance {
  const today = new Date();
  const key = `${String(today.getMonth() + 1).padStart(2, "0")}-${String(today.getDate()).padStart(2, "0")}`;
  return SIGNIFICANCE_MAP[key] ?? getDefaultSignificance(today);
}

function getTodayLabel(): string {
  return new Date().toLocaleDateString("en-IN", {
    weekday: "long", year: "numeric", month: "long", day: "numeric",
  });
}

export default function TodaySignificance() {
  const [sig, setSig] = useState<DaySignificance | null>(null);
  const [showVideo, setShowVideo] = useState(false);
  const [todayLabel, setTodayLabel] = useState("");

  useEffect(() => {
    setSig(getSignificanceForToday());
    setTodayLabel(getTodayLabel());
  }, []);

  if (!sig) return null;

  return (
    <div className="w-full max-w-4xl mx-auto mt-10 mb-4">
      {/* ── Main Card ── */}
      <div
        className="relative rounded-[32px] overflow-hidden cursor-pointer group transition-all duration-500 hover:-translate-y-1"
        style={{
          background: sig.gradient,
          boxShadow: `0 20px 60px rgba(0,0,0,0.35), 0 0 0 1px rgba(255,255,255,0.08)`,
        }}
        onClick={() => setShowVideo(true)}
      >
        {/* Noise texture overlay for depth */}
        <div
          className="absolute inset-0 pointer-events-none"
          style={{
            backgroundImage: "url(\"data:image/svg+xml,%3Csvg viewBox='0 0 256 256' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='noise'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='4' stitchTiles='stitch'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23noise)' opacity='0.04'/%3E%3C/svg%3E\")",
            opacity: 0.4,
          }}
        />

        {/* Top glow orb */}
        <div
          className="absolute -top-20 -right-20 w-64 h-64 rounded-full blur-3xl opacity-30 pointer-events-none"
          style={{ background: sig.accentColor }}
        />
        <div
          className="absolute -bottom-10 -left-10 w-48 h-48 rounded-full blur-3xl opacity-20 pointer-events-none"
          style={{ background: "rgba(255,255,255,0.3)" }}
        />

        <div className="relative p-8">
          {/* Top row */}
          <div className="flex items-center justify-between mb-6">
            <div className="flex items-center gap-3">
              <span
                className="text-[10px] font-black uppercase tracking-widest px-3 py-1.5 rounded-full"
                style={{
                  background: "rgba(255,255,255,0.18)",
                  color: "white",
                  border: "1px solid rgba(255,255,255,0.25)",
                  backdropFilter: "blur(8px)",
                }}
              >
                {sig.type}
              </span>
              <span className="text-[11px] font-medium" style={{ color: "rgba(255,255,255,0.65)" }}>
                {todayLabel}
              </span>
            </div>

            {/* Watch CTA */}
            <button
              className="flex items-center gap-2 px-5 py-2.5 rounded-full font-black text-xs transition-all group-hover:scale-105 active:scale-95 shadow-lg"
              style={{
                background: "rgba(255,255,255,0.95)",
                color: "#1e293b",
              }}
            >
              <div
                className="w-5 h-5 rounded-full flex items-center justify-center flex-shrink-0"
                style={{ background: sig.accentColor }}
              >
                <svg width="8" height="8" viewBox="0 0 24 24" fill="white"><polygon points="5,3 19,12 5,21"/></svg>
              </div>
              Watch Video
            </button>
          </div>

          {/* Emoji + Title */}
          <div className="flex items-start gap-5 mb-5">
            <div
              className="w-18 h-18 rounded-2xl flex items-center justify-center flex-shrink-0 shadow-xl text-5xl"
              style={{
                background: "rgba(255,255,255,0.15)",
                backdropFilter: "blur(12px)",
                border: "1px solid rgba(255,255,255,0.2)",
                width: 72, height: 72,
              }}
            >
              {sig.emoji}
            </div>
            <div>
              <h2
                className="font-black text-white leading-tight mb-2"
                style={{
                  fontFamily: "var(--font-playfair), Georgia, serif",
                  fontSize: "clamp(1.4rem, 3.5vw, 2rem)",
                  textShadow: "0 2px 12px rgba(0,0,0,0.3)",
                }}
              >
                {sig.title}
              </h2>
              <div className="flex items-center gap-2">
                <span className="text-lg">🙏</span>
                <p
                  className="text-sm font-bold"
                  style={{ color: sig.accentColor }}
                >
                  {sig.deity}
                </p>
              </div>
            </div>
          </div>

          {/* Description */}
          <p
            className="text-sm leading-relaxed line-clamp-3"
            style={{ color: "rgba(255,255,255,0.82)" }}
          >
            {sig.description}
          </p>

          {/* Bottom divider + hint */}
          <div
            className="flex items-center justify-between mt-6 pt-5"
            style={{ borderTop: "1px solid rgba(255,255,255,0.15)" }}
          >
            <p className="text-[11px] font-medium" style={{ color: "rgba(255,255,255,0.5)" }}>
              Click anywhere to watch today's special video →
            </p>
            <div className="flex gap-1.5">
              {[0, 1, 2].map((i) => (
                <div
                  key={i}
                  className="rounded-full transition-all"
                  style={{
                    width: i === 0 ? 20 : 6, height: 6,
                    background: i === 0 ? sig.accentColor : "rgba(255,255,255,0.25)",
                  }}
                />
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* ── Video Modal ── */}
      {showVideo && (
        <div
          className="fixed inset-0 z-[300] flex items-center justify-center p-6"
          style={{ background: "rgba(0,0,0,0.90)", backdropFilter: "blur(12px)" }}
          onClick={() => setShowVideo(false)}
        >
          <div
            className="relative w-full max-w-3xl rounded-[28px] overflow-hidden shadow-2xl"
            onClick={(e) => e.stopPropagation()}
          >
            {/* Video */}
            <div className="relative w-full" style={{ paddingTop: "56.25%" }}>
              <iframe
                src={`https://www.youtube.com/embed/${sig.videoId}?autoplay=1&rel=0`}
                className="absolute inset-0 w-full h-full"
                allow="autoplay; encrypted-media"
                allowFullScreen
                style={{ border: "none" }}
                title={sig.title}
              />
            </div>

            {/* Info panel below video */}
            <div
              className="p-5 flex items-start justify-between gap-4"
              style={{ background: sig.gradient }}
            >
              <div>
                <h3
                  className="font-black text-white text-lg"
                  style={{ fontFamily: "var(--font-playfair), Georgia, serif" }}
                >
                  {sig.title}
                </h3>
                <p className="text-xs mt-1 leading-relaxed max-w-xl line-clamp-2"
                  style={{ color: "rgba(255,255,255,0.7)" }}>
                  {sig.description}
                </p>
              </div>
              <button
                onClick={() => setShowVideo(false)}
                className="w-9 h-9 rounded-full flex items-center justify-center flex-shrink-0 text-white font-bold transition-colors hover:bg-white/20"
                style={{ background: "rgba(255,255,255,0.15)", border: "1px solid rgba(255,255,255,0.2)" }}
              >
                ✕
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
