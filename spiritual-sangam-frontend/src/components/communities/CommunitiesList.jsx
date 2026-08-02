import CommunityCard from "./CommunityCard";

const CommunitiesList = ({ communities }) => {
  if (communities.length === 0) {
    return (
      <section className="rounded-3xl bg-white p-6 shadow-sm">
        <p className="text-center text-gray-500">
          No communities found.
        </p>
      </section>
    );
  }

  return (
    <section className="space-y-5">
      {communities.map((community) => (
        <CommunityCard
          key={community.communityId}
          community={community}
        />
      ))}
    </section>
  );
};

export default CommunitiesList;