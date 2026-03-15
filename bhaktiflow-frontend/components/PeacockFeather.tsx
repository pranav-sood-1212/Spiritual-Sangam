"use client";
import { useEffect, useState } from "react";

export default function PeacockFeather({ trigger }: { trigger: number }) {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (trigger === 0) return;
    setVisible(true);
    const t = setTimeout(() => setVisible(false), 1500);
    return () => clearTimeout(t);
  }, [trigger]);

  if (!visible) return null;

  return (
    <div className="peacock-feather" aria-hidden="true">
      <svg width="80" height="220" viewBox="0 0 80 220" fill="none" xmlns="http://www.w3.org/2000/svg">
        {/* Feather stem */}
        <path d="M40 220 C40 180 38 140 40 80 C41 40 40 10 40 0" stroke="#7c5c1a" strokeWidth="2.5" strokeLinecap="round"/>
        {/* Feather barbs left */}
        {[30,50,70,90,110,130,150].map((y, i) => (
          <path key={`l${i}`} d={`M40 ${y} C30 ${y-8} 15 ${y-4} 5 ${y+5}`} stroke="#22c55e" strokeWidth="1.2" strokeLinecap="round" opacity={0.7 + i*0.04}/>
        ))}
        {/* Feather barbs right */}
        {[30,50,70,90,110,130,150].map((y, i) => (
          <path key={`r${i}`} d={`M40 ${y} C50 ${y-8} 65 ${y-4} 75 ${y+5}`} stroke="#22c55e" strokeWidth="1.2" strokeLinecap="round" opacity={0.7 + i*0.04}/>
        ))}
        {/* Eye of feather */}
        <ellipse cx="40" cy="22" rx="14" ry="18" fill="#1d4ed8" opacity="0.85"/>
        <ellipse cx="40" cy="22" rx="9" ry="12" fill="#7c3aed" opacity="0.9"/>
        <ellipse cx="40" cy="22" rx="5" ry="7" fill="#0f172a"/>
        <ellipse cx="40" cy="22" rx="2.5" ry="3.5" fill="#60a5fa" opacity="0.8"/>
        {/* Teal shimmer ring */}
        <ellipse cx="40" cy="22" rx="14" ry="18" fill="none" stroke="#06b6d4" strokeWidth="1.5" opacity="0.6"/>
        <ellipse cx="40" cy="22" rx="18" ry="22" fill="none" stroke="#22c55e" strokeWidth="1" opacity="0.4"/>
      </svg>
    </div>
  );
}
