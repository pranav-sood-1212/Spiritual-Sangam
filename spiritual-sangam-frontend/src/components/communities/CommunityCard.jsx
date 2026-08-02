import { useNavigate } from "react-router-dom";

const CommunityCard = ({ community }) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => navigate(`/community/${community.communityId}`)}
      className="cursor-pointer rounded-2xl border border-orange-100 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:border-orange-300 hover:shadow-lg"
    >
      <div className="flex items-start justify-between">
        <h2 className="text-xl font-semibold text-gray-900">
          {community.communityName}
        </h2>

        <span className="rounded-full bg-orange-100 px-3 py-1 text-sm font-medium text-orange-700">
          Community
        </span>
      </div>

      <p className="mt-4 line-clamp-2 text-gray-600">
        {community.communityDescription}
      </p>

      <div className="mt-5 flex items-center justify-between">
        <p className="text-sm text-gray-500">
          👥 {community.communityMembers} Members
        </p>

        <span className="font-medium text-orange-600">
          View Community →
        </span>
      </div>
    </div>
  );
};

export default CommunityCard;