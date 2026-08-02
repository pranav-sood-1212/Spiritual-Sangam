import { useState } from "react";

const SearchSection = ({ onSearch }) => {
  const [searchQuery, setSearchQuery] = useState("");

  return (
    <section className="rounded-3xl bg-white p-6 shadow-sm">
      <div>
        <h1 className="text-3xl font-bold text-gray-900">
          Welcome 🙏
        </h1>

        <p className="mt-2 text-gray-600">
          Discover spiritual events, temples and organizations around you.
        </p>
      </div>

      <div className="mt-6 flex gap-3">
        <input
          type="text"
          placeholder="Search communities..."
          value={searchQuery}
          onChange={(event) => setSearchQuery(event.target.value)}
          className="flex-1 rounded-2xl border border-gray-300 px-5 py-4 outline-none transition focus:border-orange-500 focus:ring-2 focus:ring-orange-100"
        />

        <button
          onClick={() => onSearch(searchQuery)}
          className="rounded-2xl bg-orange-600 px-6 font-medium text-white hover:bg-orange-700"
        >
          Search
        </button>
      </div>
    </section>
  );
};

export default SearchSection;