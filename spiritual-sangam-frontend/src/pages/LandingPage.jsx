import Hero from "../components/landing/Hero";
import Mission from "../components/landing/Mission";
import Features from "../components/landing/Features";
import EventsPreview from "../components/landing/EventsPreview";
import CallToAction from "../components/landing/CallToAction";

const LandingPage = () => {
    return(
        <>
            <Hero />
            <Mission />
            <Features />
            <EventsPreview />
            <CallToAction />
        </>
    );
};

export default LandingPage;