const SearchResultsSection = ({ searchResults }) => {
    if (searchResults.length === 0) {
        return (
            <section className="rounded-3xl bg-white p-6 shadow-sm">
            <h2 className="text-xl font-semibold text-gray-900">
                Search Results
            </h2>

            <p className="mt-6 text-gray-500">
                No communities found.
            </p>
            </section>
        );
    }

    return (
        <section className="rounded-3xl bg-white p-6 shadow-sm">
            <h2 className="text-xl font-semibold text-gray-900">
                Search Results
            </h2>
            
            <div className="mt-6 space-y-4">
                {searchResults.map((community) => (
                <div
                    key={community.communityId}
                    className="rounded-2xl border border-orange-100 p-5 transition hover:border-orange-300 hover:shadow-md"
                >
                    <h3 className="text-lg font-semibold text-gray-900">
                    {community.communityName}
                    </h3>

                    <p className="mt-2 text-gray-600">
                    {community.communityDescription}
                    </p>

                    <p className="mt-3 text-sm text-gray-500">
                    👥 {community.communityMembers} Members
                    </p>
                </div>
                ))}
            </div>
        </section>
  );
};

export default SearchResultsSection;
