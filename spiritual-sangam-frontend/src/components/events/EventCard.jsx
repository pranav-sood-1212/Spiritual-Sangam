import { useNavigate } from "react-router-dom";

const EventCard = ({ event }) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => navigate(`/event/${event.eventId}`)}
      className="cursor-pointer rounded-2xl border border-orange-100 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:border-orange-300 hover:shadow-lg"
    >
      <div className="flex items-start justify-between">
        <h2 className="text-xl font-semibold text-gray-900">
          {event.eventTitle}
        </h2>

        <span className="rounded-full bg-orange-100 px-3 py-1 text-sm font-medium text-orange-700">
          Upcoming
        </span>
      </div>

      <div className="mt-5 space-y-2 text-gray-600">
        <p>
          📅{" "}
          {new Date(event.expectedDate).toLocaleDateString("en-IN", {
            day: "numeric",
            month: "long",
            year: "numeric",
          })}
        </p>

        <p>
          🕒{" "}
          {new Date(event.startTime).toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}
          {" - "}
          {new Date(event.endTime).toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
          })}
        </p>
      </div>

      <div className="mt-6 flex items-center justify-end">
        <span className="font-medium text-orange-600">
          View Details →
        </span>
      </div>
    </div>
  );
};

export default EventCard;