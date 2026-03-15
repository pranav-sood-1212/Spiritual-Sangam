"use client";
import { useState } from "react";

const BOOKS = [
  { title: "Bhagavad Gita", author: "Ved Vyasa", pages: 701, category: "Scripture", desc: "The song of God — Krishna's discourse to Arjuna on the battlefield of Kurukshetra.", color: "#f97316" },
  { title: "Ramcharitmanas", author: "Goswami Tulsidas", pages: 1073, category: "Epic", desc: "The sacred lake of the acts of Ram, written in Awadhi by Tulsidas.", color: "#ef4444" },
  { title: "Srimad Bhagavatam", author: "Ved Vyasa", pages: 2500, category: "Purana", desc: "The story of the Lord — 18,000 verses on devotion and the life of Krishna.", color: "#8b5cf6" },
  { title: "Shiva Purana", author: "Ved Vyasa", pages: 800, category: "Purana", desc: "The glory of Lord Shiva — his legends, teachings, and the nature of reality.", color: "#06b6d4" },
  { title: "Hanuman Chalisa", author: "Goswami Tulsidas", pages: 40, category: "Stotra", desc: "Forty devotional verses in praise of Lord Hanuman by Tulsidas.", color: "#f43f5e" },
  { title: "Yoga Sutras", author: "Patanjali", pages: 196, category: "Philosophy", desc: "The classical text on Yoga — 196 sutras outlining the path to liberation.", color: "#22c55e" },
];

const CATEGORIES = ["All", "Scripture", "Epic", "Purana", "Stotra", "Philosophy"];

export default function BooksPage() {
  const [activeCategory, setActiveCategory] = useState("All");
  const filtered = activeCategory === "All" ? BOOKS : BOOKS.filter(b => b.category === activeCategory);

  return (
    <div className="w-full max-w-7xl mx-auto px-6">
      <div className="flex items-center gap-3 mb-2">
        <span className="text-5xl">📚</span>
        <div>
          <h1 className="text-5xl md:text-7xl font-black italic text-slate-900 leading-none"
            style={{ fontFamily: "'Playfair Display', serif" }}>
            Holy <span className="text-green-500">Books.</span>
          </h1>
          <p className="text-slate-500 font-medium mt-1">Sacred scriptures &amp; spiritual literature</p>
        </div>
      </div>

      {/* Category filter */}
      <div className="flex gap-2 flex-wrap mt-8 mb-8">
        {CATEGORIES.map(cat => (
          <button key={cat} onClick={() => setActiveCategory(cat)}
            className="px-4 py-2 rounded-full text-xs font-bold transition-all"
            style={{
              background: activeCategory === cat ? "#22c55e" : "rgba(255,255,255,0.7)",
              color: activeCategory === cat ? "white" : "#64748b",
              border: `1px solid ${activeCategory === cat ? "#22c55e" : "rgba(226,232,240,0.8)"}`,
            }}>
            {cat}
          </button>
        ))}
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filtered.map((book, i) => (
          <div key={i}
            className="group rounded-[24px] p-6 cursor-pointer transition-all duration-300 hover:-translate-y-1 hover:shadow-xl"
            style={{
              background: "rgba(255,255,255,0.78)",
              backdropFilter: "blur(16px)",
              border: `1px solid ${book.color}20`,
            }}>
            {/* Book spine visual */}
            <div className="flex gap-4 mb-4">
              <div className="w-16 h-20 rounded-[8px] flex-shrink-0 flex items-center justify-center shadow-lg relative overflow-hidden"
                style={{ background: `linear-gradient(135deg, ${book.color}, ${book.color}88)` }}>
                <div className="absolute left-0 top-0 bottom-0 w-2 bg-black/20 rounded-l-[8px]"/>
                <span className="text-white text-2xl">📗</span>
              </div>
              <div className="flex-1 min-w-0">
                <span className="text-[10px] font-bold uppercase tracking-wide px-2 py-0.5 rounded-full"
                  style={{ background: `${book.color}15`, color: book.color }}>
                  {book.category}
                </span>
                <h3 className="font-black text-slate-900 text-lg mt-1 leading-tight">{book.title}</h3>
                <p className="text-xs text-slate-500 mt-0.5">{book.author}</p>
              </div>
            </div>

            <p className="text-sm text-slate-600 leading-relaxed line-clamp-2">{book.desc}</p>

            <div className="flex items-center justify-between mt-4">
              <span className="text-[10px] text-slate-400 font-medium">{book.pages.toLocaleString()} pages</span>
              <button className="text-xs font-bold px-4 py-1.5 rounded-full transition-all hover:scale-105"
                style={{ background: `${book.color}15`, color: book.color }}>
                Read Now →
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
