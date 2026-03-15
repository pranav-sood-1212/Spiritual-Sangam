"use client"; // Required for interactivity

import { useState } from "react";
import { Search } from "lucide-react"; // Install with: npm install lucide-react

export default function SearchBar() {
  // 'useState' tracks what the user is typing
  const [query, setQuery] = useState("");

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Searching Spring Boot for:", query);
    // This is where you will eventually call your Java API
  };

  return (
    <form 
      onSubmit={handleSearch} 
      className="relative w-full max-w-2xl group transition-all duration-500"
    >
      {/* Search Input */}
      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder="Search for Bhajans, Deities, or Moods..."
        className="w-full bg-white/40 border border-white/60 px-8 py-5 rounded-[28px] outline-none focus:ring-4 focus:ring-orange-500/10 transition-all backdrop-blur-xl text-slate-800 placeholder:text-slate-400 text-lg shadow-sm"
      />
      
      {/* Search Button */}
      <button 
        type="submit" 
        className="absolute right-4 top-1/2 -translate-y-1/2 p-3 bg-orange-500 hover:bg-orange-600 text-white rounded-2xl transition-all shadow-lg shadow-orange-200"
      >
        <Search size={22} />
      </button>
    </form>
  );
}