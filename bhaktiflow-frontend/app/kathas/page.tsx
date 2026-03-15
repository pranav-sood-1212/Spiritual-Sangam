"use client";
export default function KathasPage() {
  const kathas = [
    { title: "Shiv Puran Katha", duration: "2h 30m", speaker: "Pt. Pradeep Mishra", deity: "Shiva", thumb: "/lordShiva.jpeg" },
    { title: "Bhagavat Katha", duration: "7 Days", speaker: "Pt. Indresh Upadhyay", deity: "Krishna", thumb: "/shreeKrishna.jpeg" },
    { title: "Ram Katha", duration: "9 Days", speaker: "Morari Bapu", deity: "Ram", thumb: "/shreeRam.jpeg" },
    { title: "Hanuman Katha", duration: "3h 15m", speaker: "Pt. Ramesh Bhai Ojha", deity: "Hanuman", thumb: "/lordHanuman.jpeg" },
    { title: "Devi Bhagavat", duration: "5 Days", speaker: "Pt. Vijay Shankar Mehta", deity: "Devi", thumb: "/lordShiva.jpeg" },
    { title: "Satyanarayan Katha", duration: "1h 45m", speaker: "Various", deity: "Vishnu", thumb: "/shreeKrishna.jpeg" },
  ];

  return (
    <div className="w-full max-w-7xl mx-auto px-6">
      <div className="flex items-center gap-3 mb-2">
        <span className="text-5xl">📖</span>
        <div>
          <h1 className="text-5xl md:text-7xl font-black italic text-slate-900 leading-none"
            style={{ fontFamily: "'Playfair Display', serif" }}>
            Divine <span className="text-red-500">Kathas.</span>
          </h1>
          <p className="text-slate-500 font-medium mt-1">Sacred stories &amp; spiritual discourses</p>
        </div>
      </div>

      <div className="mt-12 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {kathas.map((katha, i) => (
          <div
            key={i}
            className="group relative rounded-[24px] overflow-hidden cursor-pointer transition-all duration-300 hover:-translate-y-2"
            style={{ boxShadow: "0 8px 32px rgba(0,0,0,0.12)" }}
          >
            <div className="aspect-video relative overflow-hidden">
              <img src={katha.thumb} alt={katha.title}
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"/>
              <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent"/>
              {/* Play button overlay */}
              <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                <div className="w-14 h-14 rounded-full flex items-center justify-center shadow-2xl"
                  style={{ background: "rgba(239,68,68,0.9)" }}>
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="white"><polygon points="6,3 20,12 6,21"/></svg>
                </div>
              </div>
              <div className="absolute bottom-3 left-3">
                <span className="text-[10px] font-bold text-white bg-black/50 px-2 py-1 rounded-full">
                  {katha.duration}
                </span>
              </div>
            </div>
            <div className="p-4" style={{ background: "rgba(255,255,255,0.85)", backdropFilter: "blur(16px)" }}>
              <h3 className="font-black text-slate-900 text-base leading-tight">{katha.title}</h3>
              <p className="text-xs text-slate-500 mt-1">{katha.speaker}</p>
              <span className="inline-block mt-2 text-[10px] font-bold uppercase px-2.5 py-1 rounded-full"
                style={{ background: "rgba(239,68,68,0.1)", color: "#ef4444" }}>
                {katha.deity}
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
