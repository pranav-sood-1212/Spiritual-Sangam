import { useEffect, useState } from "react";
import { getFeaturedCommunities } from "../../api/communityApi";
import { useNavigate } from "react-router-dom";

const FeaturedCommunitiesSection = () => {
  const [communities, setCommunities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleClick = (communityId) => {
    navigate(`/community/${communityId}`)
  }
  const handleExploreCommunitiesClick = () => {
    navigate("/communities")
  }

  useEffect(()=>{
    const fetchFeaturedCommunities = async () => {
      
      try {
        const data = await getFeaturedCommunities();
        setCommunities(data);
      } catch {
        setError("Unable to load featured communities.");
      } finally {
        setLoading(false)
      }
    }

    fetchFeaturedCommunities();
  },[]);

    if (loading) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Featured Communities
        </h2>

        <p className="mt-6 text-gray-500">
          Loading communities...
        </p>
      </section>
    );
  }

  if (error) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Featured Communities
        </h2>

        <p className="mt-6 text-red-500">
          {error}
        </p>
      </section>
    );
  }

  if (communities.length === 0) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Featured Communities
        </h2>

        <p className="mt-6 text-gray-500">
          No communities available.
        </p>
      </section>
    );
  }


  return (
    <section className="rounded-3xl bg-white p-6 shadow-sm">
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-semibold text-gray-900">
          Featured Communities
        </h2>

        <button 
              onClick={()=>{handleExploreCommunitiesClick()}}
              className="text-sm font-medium text-orange-600 hover:text-orange-700">
          Explore
        </button>
      </div>

      <div className="mt-6 space-y-4">
        {communities.map((community) => (
          <div
            key={community.communityId}
            className="cursor-pointer rounded-2xl border border-orange-100 p-5 transition hover:border-orange-300 hover:shadow-md"
            onClick={() => handleClick(community.communityId)}
          >
            <h3 className="text-lg font-semibold text-gray-900">
              {community.communityName}
            </h3>

            <p className="mt-2 text-gray-600">
              {community.communityDescription}
            </p>

            <p className="mt-3 text-sm text-gray-500">
              👥 {community.communityMembers} Members
            </p>
          </div>
        ))}
      </div>
    </section>
  );
};

export default FeaturedCommunitiesSection;