import { useEffect, useState } from "react";
import { getUpcomingEvents } from "../../api/eventApi";
import { useNavigate } from "react-router-dom";

const UpcomingEventsSection = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate=useNavigate();

  const handleExploreEventsClick=()=>{
    navigate("/events");
  }

  const handleEventClick=(eventId)=>{
    navigate(`/event/${eventId}`);
  }

  useEffect(() => {
    const fetchUpcomingEvents = async () => {
      try {
        const data = await getUpcomingEvents();
        setEvents(data);
      } catch (err) {
        setError("Unable to load upcoming events.");
      } finally {
        setLoading(false);
      }
    };

    fetchUpcomingEvents();
  }, []);

  if (loading) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Upcoming Events
        </h2>

        <p className="mt-6 text-gray-500">
          Loading events...
        </p>
      </section>
    );
  }

  if (error) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Upcoming Events
        </h2>

        <p className="mt-6 text-red-500">
          {error}
        </p>
      </section>
    );
  }

  if (events.length === 0) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <h2 className="text-xl font-semibold text-gray-900">
          Upcoming Events
        </h2>

        <p className="mt-6 text-gray-500">
          No upcoming events available.
        </p>
      </section>
    );
  }

  return (
    <section className="rounded-3xl bg-white p-6 shadow-sm">
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-semibold text-gray-900">
          Upcoming Events
        </h2>

        <button 
          onClick={()=>handleExploreEventsClick()}
          className="text-sm font-medium text-orange-600 hover:text-orange-700">
          Explore
        </button>
      </div>

      <div className="mt-6 space-y-4">
        {events.map((event) => (
          <div
            key={event.eventId}
            className="rounded-2xl border border-orange-100 p-5 transition hover:border-orange-300 hover:shadow-md"
            onClick={()=>{handleEventClick(event.eventId)}}
          >
            <h3 className="text-lg font-semibold text-gray-900">
              {event.eventTitle}
            </h3>

            <p className="mt-2 text-gray-600">
              {event.expectedDate}
            </p>

            <p className="mt-1 text-sm text-gray-500">
              {event.startTime} - {event.endTime}
            </p>
          </div>
        ))}
      </div>
    </section>
  );
};

export default UpcomingEventsSection;