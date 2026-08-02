const features = [
  {
    title: "Discover Events",
    description:
      "Explore spiritual gatherings, festivals, satsangs, and temple events happening around you."
  },
  {
    title: "Temple Communities",
    description:
      "Follow temples and organizations to stay updated with announcements and activities."
  },
  {
    title: "Meaningful Discussions",
    description:
      "Ask questions, share experiences, and participate in respectful spiritual conversations."
  },
  {
    title: "Personalized Experience",
    description:
      "Build your profile, save events, and follow communities that inspire your spiritual journey."
  }
];

const Features = () => {
  return (
    <section className="bg-orange-50 py-20">
      <div className="mx-auto max-w-7xl px-6">
        <div className="text-center">
          <p className="text-sm font-semibold uppercase tracking-widest text-orange-600">
            Features
          </p>

          <h2 className="mt-3 text-3xl font-bold text-gray-900 md:text-4xl">
            Everything You Need in One Spiritual Platform
          </h2>
        </div>

        <div className="mt-14 grid gap-8 md:grid-cols-2">
          {features.map((feature) => (
            <div
              key={feature.title}
              className="rounded-2xl bg-white p-8 shadow-sm transition hover:shadow-md"
            >
              <h3 className="text-xl font-semibold text-gray-900">
                {feature.title}
              </h3>

              <p className="mt-4 leading-7 text-gray-600">
                {feature.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default Features;