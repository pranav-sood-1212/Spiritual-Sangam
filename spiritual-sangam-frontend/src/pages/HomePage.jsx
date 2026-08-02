import BottomNavigation from "../components/home/Bottomnavigation";
import RecentDiscussionsSection from "../components/home/RecentDiscussionsSection";
import UpcomingEventsSection from "../components/home/UpcomingEventsSection";
import FeaturedCommunitiesSection from "../components/home/FeaturedCommunitiesSection";
import SearchSection from "../components/home/SearchSection";
import SearchResultsSection from "../components/home/SearchResultsSection";
import { useState } from "react";
import { searchCommunities } from "../api/communityApi";

const HomePage = () => {
  const [searchResults, setSearchResults] = useState([]);
  const [hasSearched, setHasSearched] = useState(false);

  const handleSearch = async (query) => {
    try{
        const data = await searchCommunities(query);
        setSearchResults(data);
        setHasSearched(true);
    }
    catch(error){
        console.error(error);
    }
  };
  return (
    <main className="min-h-screen bg-orange-50 pb-24">
      <div className="mx-auto flex w-full max-w-6xl flex-col gap-10 px-6 py-8">
        <SearchSection onSearch={handleSearch} />

        {hasSearched && (
            <SearchResultsSection
                searchResults={searchResults}
            />
        )}

        <UpcomingEventsSection />

        <FeaturedCommunitiesSection />
      </div>

      <BottomNavigation />
    </main>
  );
};

export default HomePage;