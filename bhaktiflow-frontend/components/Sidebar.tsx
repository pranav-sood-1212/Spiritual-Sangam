"use client";
import { useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";

const CATEGORIES = [
  { id: "bhajans",   label: "Bhajans",   href: "/bhajans",   emoji: "🎵", color: "#f97316", desc: "Sacred songs"       },
  { id: "poems",     label: "Poems",     href: "/poems",     emoji: "📜", color: "#8b5cf6", desc: "Spiritual verses"   },
  { id: "kathas",    label: "Kathas",    href: "/kathas",    emoji: "📖", color: "#ef4444", desc: "Divine stories"     },
  { id: "paintings", label: "Paintings", href: "/paintings", emoji: "🎨", color: "#06b6d4", desc: "Sacred art"         },
  { id: "books",     label: "Books",     href: "/books",     emoji: "📚", color: "#22c55e", desc: "Holy scriptures"    },
];

export default function Sidebar() {
  const [isOpen, setIsOpen] = useState(false);
  const pathname = usePathname();

  return (
    <>
      {/* Toggle tab — always visible on left edge */}
      <button
        onClick={() => setIsOpen((p) => !p)}
        className="fixed left-0 top-1/2 -translate-y-1/2 z-[200] flex items-center justify-center transition-all duration-300 hover:scale-105 active:scale-95"
        style={{
          width: 28, height: 72,
          background: "rgba(255,255,255,0.85)",
          backdropFilter: "blur(12px)",
          border: "1px solid rgba(255,255,255,0.9)",
          borderLeft: "none",
          borderRadius: "0 16px 16px 0",
          boxShadow: "4px 0 20px rgba(0,0,0,0.08)",
        }}
        aria-label="Toggle sidebar"
      >
        <svg
          width="12" height="12" viewBox="0 0 24 24"
          fill="none" stroke="#f97316" strokeWidth="3" strokeLinecap="round"
          style={{ transform: isOpen ? "rotate(180deg)" : "rotate(0deg)", transition: "transform 0.3s ease" }}
        >
          <polyline points="9,18 15,12 9,6"/>
        </svg>
      </button>

      {/* Backdrop */}
      {isOpen && (
        <div
          className="fixed inset-0 z-[150]"
          style={{ background: "rgba(0,0,0,0.15)", backdropFilter: "blur(2px)" }}
          onClick={() => setIsOpen(false)}
        />
      )}

      {/* Sidebar panel */}
      <div
        className="fixed top-0 left-0 h-full z-[180] flex flex-col"
        style={{
          width: 220,
          background: "rgba(255,255,255,0.90)",
          backdropFilter: "blur(24px)",
          WebkitBackdropFilter: "blur(24px)",
          border: "1px solid rgba(255,255,255,0.9)",
          borderLeft: "none",
          boxShadow: "8px 0 40px rgba(0,0,0,0.12)",
          borderRadius: "0 24px 24px 0",
          transform: isOpen ? "translateX(0)" : "translateX(-100%)",
          transition: "transform 0.35s cubic-bezier(0.25, 0.46, 0.45, 0.94)",
        }}
      >
        {/* Logo */}
        <div className="px-5 pt-6 pb-4 border-b border-slate-100">
          <Link href="/" onClick={() => setIsOpen(false)} className="flex items-center gap-2">
            <div className="w-8 h-8 bg-orange-500 rounded-lg flex items-center justify-center shadow">
              <span className="text-white font-black text-sm">B</span>
            </div>
            <span className="font-black text-slate-900 text-base">
              Bhakti<span className="text-orange-500">Flow.</span>
            </span>
          </Link>
          <p className="text-[10px] text-slate-400 mt-1 font-medium">Sacred Frequencies</p>
        </div>

        {/* Nav links */}
        <nav className="flex-1 px-3 py-4 flex flex-col gap-1 overflow-y-auto">
          <p className="text-[9px] font-black text-slate-400 uppercase tracking-widest px-2 mb-2">
            Categories
          </p>
          {CATEGORIES.map((cat) => {
            const isActive = pathname === cat.href;
            return (
              <Link
                key={cat.id}
                href={cat.href}
                onClick={() => setIsOpen(false)}
                className="flex items-center gap-3 px-3 py-2.5 rounded-2xl transition-all duration-200 group"
                style={{
                  background: isActive ? `${cat.color}18` : "transparent",
                  border: isActive ? `1px solid ${cat.color}30` : "1px solid transparent",
                }}
              >
                <div
                  className="w-9 h-9 rounded-xl flex items-center justify-center text-lg flex-shrink-0 transition-transform group-hover:scale-110"
                  style={{ background: `${cat.color}15` }}
                >
                  {cat.emoji}
                </div>
                <div className="min-w-0">
                  <p className="text-sm font-bold leading-tight" style={{ color: isActive ? cat.color : "#1e293b" }}>
                    {cat.label}
                  </p>
                  <p className="text-[10px] text-slate-400 font-medium">{cat.desc}</p>
                </div>
                {isActive && (
                  <div className="w-1.5 h-1.5 rounded-full ml-auto flex-shrink-0" style={{ background: cat.color }}/>
                )}
              </Link>
            );
          })}
        </nav>

        {/* Close button */}
        <div className="px-4 pb-6">
          <button
            onClick={() => setIsOpen(false)}
            className="w-full py-2.5 rounded-2xl text-xs font-bold text-slate-500 hover:text-slate-700 transition-colors"
            style={{ background: "rgba(241,245,249,0.8)", border: "1px solid rgba(226,232,240,0.6)" }}
          >
            ← Close
          </button>
        </div>
      </div>
    </>
  );
}
