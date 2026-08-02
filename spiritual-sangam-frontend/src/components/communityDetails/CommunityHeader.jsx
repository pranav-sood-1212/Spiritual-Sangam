import { joinCommunity } from "../../api/communityApi";

const CommunityHeader = ({ community }) => {
    const handleJoinClick = async () => {
        try {
        const response = await joinCommunity(community.communityId);
        alert(response.message);
    } catch (error) {
        console.log(error);

        if (error.response) {
            alert(error.response.data.message);
        }
    }
    }
    return (
        <section className="rounded-3xl bg-white p-6 shadow-sm">

            <h1 className="text-3xl font-bold text-gray-900">
                {community.communityName}
            </h1>

            <p className="mt-2 text-gray-600">
                Hosted by {community.hostName}
            </p>

            <div className="mt-4 flex items-center justify-between">

                <p className="text-gray-700">
                    👥 {community.communityMembers} Members
                </p>

                <button
                    onClick={() => {handleJoinClick(community)}} 
                    className="rounded-xl bg-orange-600 px-5 py-2 font-medium text-white hover:bg-orange-700"
                    >
                    Join Community
                </button>

            </div>

        </section>
    );
};

export default CommunityHeader;