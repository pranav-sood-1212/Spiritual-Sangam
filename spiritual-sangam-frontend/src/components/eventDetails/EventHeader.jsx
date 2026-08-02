import { registerInEvent } from "../../api/eventApi";
const EventHeader = ({ event }) => {
    const handleRegisterClick = async () => {
        try {
            const data = await registerInEvent(event.eventId);
            console.log(data.message)
        } catch (error) {
            if (error.response) {
                alert(error.response.data.message);
            }
        }
    }
    return (
        <section className="rounded-3xl bg-white p-6 shadow-sm">

            <h1 className="text-3xl font-bold text-gray-900">
                {event.eventTitle}
            </h1>

            <div className="mt-4 flex items-center justify-between">

                <p className="text-gray-700">
                    👥 {event.totalSeats} Total Seats
                    

                </p>
                <p className="text-gray-700">
                    👥 {event.leftSeats} Left Seats

                </p>

                <button
                    onClick={() => {handleRegisterClick(event)}} 
                    className="rounded-xl bg-orange-600 px-5 py-2 font-medium text-white hover:bg-orange-700"
                    >
                    RegisterInEvent
                </button>

            </div>

        </section>
    );
};

export default EventHeader;