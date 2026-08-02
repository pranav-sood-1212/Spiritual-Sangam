import { useState } from "react";
import { useEffect } from "react";
import CommunitiesHeader from "../components/communities/CommunitiesHeader";
import CommunitiesList from "../components/communities/CommunitiesList";
import BottomNavigation from "../components/home/BottomNavigation";
import { getAllCommunities } from "../api/communityApi";

const CommunitiesPage = () =>{
    const [communities,setCommunities]=useState([]);
    const [loading,setLoading]=useState(true);
    const [error,setError]=useState("");

    useEffect(() => {
        const fetchCommunities = async () => {
          try {
            const data = await getAllCommunities();
            setCommunities(data);
          } catch {
            setError("Unable to load communities.");
          } finally {
            setLoading(false);
          }
        };
    
        fetchCommunities();
      }, []);

      if(loading){
        return (
            <section className="rounded-3xl bg-white p-6 shadow-sm">
                <h2 className="text-xl font-semibold">Events</h2>
                <p className="mt-4 text-gray-500">Loading events...</p>
            </section>
        );
      }

      if(error){
        return (
            <section className="rounded-3xl bg-white p-6 shadow-sm">
                <h2 className="text-xl font-semibold">Events</h2>
                <p className="mt-4 text-red-500">{error}</p>
            </section>
        );
      }
    return(
        <main>
            <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-8">
                <CommunitiesHeader />

                <CommunitiesList communities={communities} />
            </div>

            <BottomNavigation />
        </main>
    )
}
export default CommunitiesPage;