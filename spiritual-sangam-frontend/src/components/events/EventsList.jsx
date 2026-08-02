import EventCard from "./EventCard";

const EventsList = ({ events }) => {
  if (events.length === 0) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <p className="text-center text-gray-500">
          No upcoming events available.
        </p>
      </section>
    );
  }

  return (
    <section className="space-y-5">
      {events.map((event) => (
        <EventCard
          key={event.eventId}
          event={event}
        />
      ))}
    </section>
  );
};

export default EventsList;