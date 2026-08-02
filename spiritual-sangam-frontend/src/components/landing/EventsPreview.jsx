const events = [
  {
    title: "Morning Bhagavad Gita Study",
    temple: "ISKCON Chandigarh",
    date: "20 July 2026",
    location: "Chandigarh"
  },
  {
    title: "Guru Purnima Celebration",
    temple: "Shri Jagannath Mandir",
    date: "24 July 2026",
    location: "Sector 31"
  },
  {
    title: "Evening Satsang & Bhajan",
    temple: "Shri Krishna Temple",
    date: "28 July 2026",
    location: "Panchkula"
  }
];

const EventsPreview = () => {
  return (
    <section className="bg-white py-20">
      <div className="mx-auto max-w-7xl px-6">
        <div className="text-center">
          <p className="text-sm font-semibold uppercase tracking-widest text-orange-600">
            Upcoming Events
          </p>

          <h2 className="mt-3 text-3xl font-bold text-gray-900 md:text-4xl">
            Experience Spiritual Gatherings Near You
          </h2>

          <p className="mx-auto mt-4 max-w-2xl text-gray-600">
            Discover temple events, satsangs, festivals, and community
            gatherings happening around you.
          </p>
        </div>

        <div className="mt-14 grid gap-6 lg:grid-cols-3">
          {events.map((event) => (
            <article
              key={event.title}
              className="rounded-2xl border border-gray-200 bg-white p-6 shadow-sm transition hover:shadow-md"
            >
              <h3 className="text-xl font-semibold text-gray-900">
                {event.title}
              </h3>

              <p className="mt-3 text-gray-600">
                {event.temple}
              </p>

              <div className="mt-6 space-y-2 text-sm text-gray-500">
                <p>
                  <span className="font-medium">Date:</span> {event.date}
                </p>

                <p>
                  <span className="font-medium">Location:</span> {event.location}
                </p>
              </div>
            </article>
          ))}
        </div>
      </div>
    </section>
  );
};

export default EventsPreview;