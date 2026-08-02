const EventAbout = ({ event }) => {
    return (
        <section className="mt-6 rounded-3xl bg-white p-6 shadow-sm">

            <h2 className="text-xl font-semibold">
                About
            </h2>

            <p className="mt-3 text-gray-600">
                {event.eventDescription}
            </p>

        </section>
    );
};

export default EventAbout;