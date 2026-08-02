import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { detailsOfCommunity } from "../api/communityApi";
import CommunityHeader from "../components/communityDetails/CommunityHeader";
import CommunityAbout from "../components/communityDetails/CommunityAbout";
import { useState } from "react";

const CommunityDetailsPage = () => {
    const [community, setCommunity] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const { communityId } = useParams();

    console.log(communityId);

    useEffect(() => {
        const fetchCommunityDetails = async() => {
            try {
                const data = await detailsOfCommunity(communityId);
                console.log(data);
                setCommunity(data);
            } catch (error) {
                setError("Unable to load community.");
            } finally {
                setLoading(false)
            }
        }
        fetchCommunityDetails();
    },[communityId])



    if (loading) {
        return (
        <section className="rounded-3xl bg-white p-6 shadow-sm">
            <h2 className="text-xl font-semibold text-gray-900">
            Community 
            </h2>

            <p className="mt-6 text-gray-500">
            Loading community...
            </p>
        </section>
        );
    }

  if (error) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Community
        </h2>

        <p className="mt-6 text-red-500">
          {error}
        </p>
      </section>
    );
  }

  if (community==null) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Community
        </h2>

        <p className="mt-6 text-gray-500">
          No details for this community
        </p>
      </section>
    );
  }  

    return (
        <main className="min-h-screen bg-orange-50 pb-24">
            <div className="mx-auto flex w-full max-w-6xl flex-col gap-6 px-6 py-8">

                <CommunityHeader community={community} />

                <CommunityAbout community={community} />

            </div>
        </main>
    );
};

export default CommunityDetailsPage;