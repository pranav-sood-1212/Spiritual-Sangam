"use client";
import Link from "next/link";

export default function Header() {
  return (
    <header className="fixed top-4 left-1/2 -translate-x-1/2 z-50 w-[95%] max-w-6xl">
      <div className="bg-white/80 backdrop-blur-2xl border border-white/90 rounded-[20px] px-6 py-3 shadow-lg flex items-center justify-between">
        {/* Logo */}
        <Link href="/" className="flex items-center gap-2">
          <div className="w-8 h-8 bg-orange-500 rounded-lg flex items-center justify-center shadow-md">
            <span className="text-white font-black text-sm">B</span>
          </div>
          <span
            className="font-black text-slate-900 text-lg tracking-tight"
            style={{ fontFamily: "'DM Sans', sans-serif" }}
          >
            Bhakti<span className="text-orange-500">Flow.</span>
          </span>
        </Link>

        {/* Nav */}
        <nav className="hidden md:flex items-center gap-8">
          {["Home", "Library", "Moods"].map((item) => (
            <Link
              key={item}
              href="/"
              className="text-sm font-semibold text-slate-700 hover:text-orange-500 transition-colors"
            >
              {item}
            </Link>
          ))}
        </nav>

        {/* User */}
        <div className="flex items-center gap-2 bg-slate-900 text-white px-4 py-2 rounded-full text-sm font-bold shadow">
          <div className="w-6 h-6 bg-orange-500 rounded-full flex items-center justify-center text-xs font-black">
            PS
          </div>
          <span>Pranav</span>
        </div>
      </div>
    </header>
  );
}
