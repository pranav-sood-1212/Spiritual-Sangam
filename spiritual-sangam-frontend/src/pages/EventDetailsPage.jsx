import { useParams } from "react-router-dom";
import { useState , useEffect } from "react";
import EventHeader from "../components/eventDetails/EventHeader";
import EventAbout from "../components/eventDetails/EventAbout";
import { detailsOfEvent } from "../api/eventApi";

const EventDetailsPage = () => {
    const [event, setEvent] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const { eventId } = useParams();

    console.log(eventId);

    useEffect(() => {
        const fetchEventDetails = async() => {
            try {
                const data = await detailsOfEvent(eventId);
                console.log(data);
                setEvent(data);
            } catch (error) {
                setError("Unable to load event.");
            } finally {
                setLoading(false)
            }
        }
        fetchEventDetails();
    },[eventId])



    if (loading) {
        return (
        <section className="rounded-3xl bg-white p-6 shadow-sm">
            <h2 className="text-xl font-semibold text-gray-900">
            Event 
            </h2>

            <p className="mt-6 text-gray-500">
            Loading event...
            </p>
        </section>
        );
    }

  if (error) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Event
        </h2>

        <p className="mt-6 text-red-500">
          {error}
        </p>
      </section>
    );
  }

  if (event==null) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Event
        </h2>

        <p className="mt-6 text-gray-500">
          No details for this event
        </p>
      </section>
    );
  }  

    return (
        <main className="min-h-screen bg-orange-50 pb-24">
            <div className="mx-auto flex w-full max-w-6xl flex-col gap-6 px-6 py-8">

                <EventHeader event={event} />
                <EventAbout event={event} />

            </div>
        </main>
    );
};

export default EventDetailsPage;