import { useEffect, useState } from "react";
import { getAllEvents } from "../api/eventApi";
import EventsHeader from "../components/events/EventsHeader";
import EventsList from "../components/events/EventsList";
import BottomNavigation from "../components/home/BottomNavigation";

const EventsPage = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const data = await getAllEvents();
        setEvents(data);
      } catch {
        setError("Unable to load events.");
      } finally {
        setLoading(false);
      }
    };

    fetchEvents();
  }, []);

  if (loading) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold">Events</h2>
        <p className="mt-4 text-gray-500">Loading events...</p>
      </section>
    );
  }

  if (error) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold">Events</h2>
        <p className="mt-4 text-red-500">{error}</p>
      </section>
    );
  }

  return (
    <main className="min-h-screen bg-orange-50 pb-24">
      <div className="mx-auto flex max-w-6xl flex-col gap-8 px-6 py-8">
        <EventsHeader />

        <EventsList events={events} />
      </div>

      <BottomNavigation />
    </main>
  );
};

export default EventsPage;