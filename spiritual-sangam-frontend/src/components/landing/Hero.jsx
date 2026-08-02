const Hero = () => {
  return (
    <section className="bg-gradient-to-b from-orange-50 to-white">
      <div className="mx-auto flex min-h-[80vh] max-w-7xl flex-col items-center justify-center px-6 text-center">
        <p className="mb-4 text-sm font-medium uppercase tracking-widest text-orange-600">
          Connecting Devotees • Temples • Communities
        </p>

        <h1 className="max-w-4xl text-4xl font-bold leading-tight text-gray-900 md:text-6xl">
          Discover Spiritual Communities Around You
        </h1>

        <p className="mt-6 max-w-2xl text-lg leading-8 text-gray-600">
          Spiritual Sangam helps devotees discover temple events,
          join meaningful discussions, connect with organizations,
          and stay spiritually engaged—all in one peaceful platform.
        </p>

        <div className="mt-10 flex flex-col gap-4 sm:flex-row">
          <button className="rounded-full bg-orange-600 px-8 py-3 font-medium text-white transition hover:bg-orange-700">
            Explore Events
          </button>

          <button className="rounded-full border border-orange-600 px-8 py-3 font-medium text-orange-600 transition hover:bg-orange-50">
            Learn More
          </button>
        </div>
      </div>
    </section>
  );
};

export default Hero;