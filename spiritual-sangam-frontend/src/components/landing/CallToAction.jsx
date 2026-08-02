import { Link } from "react-router-dom";

const CallToAction = () => {
  return (
    <section className="bg-orange-600 py-20">
      <div className="mx-auto max-w-4xl px-6 text-center">
        <h2 className="text-3xl font-bold text-white md:text-4xl">
          Begin Your Spiritual Journey Today
        </h2>

        <p className="mx-auto mt-6 max-w-2xl text-lg leading-8 text-orange-100">
          Discover inspiring events, connect with temples and spiritual
          communities, and become part of a platform built to bring devotees
          together.
        </p>

        <div className="mt-10 flex flex-col justify-center gap-4 sm:flex-row">
          <Link 
              to="/login"
              className="rounded-full bg-white px-8 py-3 font-semibold text-orange-600 transition hover:bg-orange-50">
            Explore Events
          </Link>

          <Link 
              to="/register"
              className="rounded-full border border-white px-8 py-3 font-semibold text-white transition hover:bg-orange-500">
            Get Started
          </Link>
        </div>
      </div>
    </section>
  );
};

export default CallToAction;