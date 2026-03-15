"use client";
export default function PoemsPage() {
  return (
    <div className="w-full max-w-7xl mx-auto px-6">
      <div className="flex items-center gap-3 mb-2">
        <span className="text-5xl">📜</span>
        <div>
          <h1 className="text-5xl md:text-7xl font-black italic text-slate-900 leading-none"
            style={{ fontFamily: "'Playfair Display', serif" }}>
            Spiritual <span className="text-purple-500">Poems.</span>
          </h1>
          <p className="text-slate-500 font-medium mt-1">Sacred verses &amp; devotional poetry</p>
        </div>
      </div>

      <div className="mt-12 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {/* Placeholder cards — connect to your backend */}
        {[
          { title: "Meera Bai Bhajan", lines: "Mero toh Giridhar Gopal, doosro na koi...", deity: "Krishna" },
          { title: "Kabir Doha", lines: "Dukh mein simran sab kare, sukh mein kare na koi...", deity: "Nirgun" },
          { title: "Tulsidas Ramayan", lines: "Mangal bhavan amangal haari, dravihu su dasrath ajir bihaari...", deity: "Ram" },
          { title: "Surdas Pad", lines: "Prabhu mere avgun chit na dharo...", deity: "Krishna" },
          { title: "Mirabai Pad", lines: "Paayo ji maine Ram ratan dhan paayo...", deity: "Ram" },
          { title: "Narsi Mehta", lines: "Vaishnav jan to tene kahiye, je peed paraayi jaane re...", deity: "Vishnu" },
        ].map((poem, i) => (
          <div
            key={i}
            className="group rounded-[24px] p-6 cursor-pointer transition-all duration-300 hover:-translate-y-1"
            style={{
              background: "rgba(255,255,255,0.75)",
              backdropFilter: "blur(16px)",
              border: "1px solid rgba(139,92,246,0.15)",
              boxShadow: "0 4px 24px rgba(139,92,246,0.08)",
            }}
          >
            <div className="w-10 h-10 rounded-xl flex items-center justify-center text-xl mb-4"
              style={{ background: "rgba(139,92,246,0.1)" }}>
              📜
            </div>
            <h3 className="font-black text-slate-900 text-lg mb-2">{poem.title}</h3>
            <p className="text-slate-500 text-sm leading-relaxed italic line-clamp-2">"{poem.lines}"</p>
            <div className="mt-4 flex items-center justify-between">
              <span className="text-[10px] font-bold uppercase tracking-wide px-2.5 py-1 rounded-full"
                style={{ background: "rgba(139,92,246,0.1)", color: "#8b5cf6" }}>
                {poem.deity}
              </span>
              <span className="text-[10px] text-slate-400 font-medium group-hover:text-purple-500 transition-colors">
                Read more →
              </span>
            </div>
          </div>
        ))}
      </div>

      <div className="mt-12 text-center">
        <p className="text-slate-400 text-sm">Connect your backend to load poems from MongoDB</p>
      </div>
    </div>
  );
}
