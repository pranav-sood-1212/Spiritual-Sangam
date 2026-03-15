"use client";

interface DeityProps {
  name: string;
  shlok: string;
  imageUrl: string; // Add your deity images here
  onClick: () => void;
}

export default function DeityCard({ name, shlok, imageUrl, onClick }: DeityProps) {
  return (
    <div onClick={onClick} className="group relative w-full h-[450px] rounded-[48px] overflow-hidden shadow-2xl transition-all duration-700 hover:scale-[1.02]">
      
      {/* 1. The Background Image (Emerging from bottom) */}
      <img 
        src={imageUrl} 
        alt={name}
        className="absolute inset-0 w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
      />

      {/* 2. The Foggy Gradient Overlay (Bottom-to-Top) */}
      <div className="absolute inset-0 bg-gradient-to-t from-black/90 via-black/20 to-transparent transition-opacity duration-500 group-hover:from-orange-900/80" />

      {/* 3. Text Content */}
      <div className="absolute inset-0 flex flex-col justify-end p-10">
        <h3 className="text-4xl font-black italic tracking-tighter text-white mb-2 translate-y-4 group-hover:translate-y-0 transition-transform duration-500">
          {name}
        </h3>
        
        <p className="text-sm font-medium italic text-orange-200/80 leading-relaxed translate-y-4 group-hover:translate-y-0 transition-transform duration-500 delay-75">
          "{shlok}"
        </p>

        {/* 4. Subtle "Explore" indicator that slides up on hover */}
        <div className="mt-6 w-12 h-1 bg-orange-500 rounded-full opacity-0 group-hover:opacity-100 group-hover:w-full transition-all duration-700" />
      </div>
    </div>
  );
}