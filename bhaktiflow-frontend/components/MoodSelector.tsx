"use client";

interface MoodSelectorProps {
  onSelect: (id: string) => void;
  activeMood: string | null; 
}

const MOODS = [
  { id: "Brahma Muhurta", label: "Brahma Muhurta", icon: "🌅" },
  { id: "Peaceful Vibe", label: "Peaceful Vibe", icon: "🍃" },
  { id: "Energetic Vibe", label: "Energetic Vibe", icon: "🔥" },
  { id: "Evening Aartis", label: "Evening Aarti", icon: "🌙" },
  { id: "Morning Aartis", label: "Morning Aarti", icon: "🧘" },
];

export default function MoodSelector({ onSelect, activeMood }: MoodSelectorProps) {
  return (
    <div className="w-full py-4">
      <div className="flex gap-4 overflow-x-auto pb-4 no-scrollbar px-2">
        {MOODS.map((mood) => (
          <button
            key={mood.id}
            onClick={() => onSelect(mood.id)}
            className={`flex-shrink-0 flex items-center gap-3 px-6 py-3 rounded-2xl border transition-all duration-300 ${
              activeMood === mood.id
                ? "bg-orange-500 border-orange-500 text-white shadow-lg shadow-orange-200 scale-105"
                : "bg-white/40 border-white/60 text-slate-700 hover:bg-white/60 backdrop-blur-md"
            }`}
          >
            <span className="text-xl">{mood.icon}</span>
            <span className="font-bold text-sm uppercase tracking-widest">{mood.label}</span>
          </button>
        ))}
      </div>
    </div>
  );
}