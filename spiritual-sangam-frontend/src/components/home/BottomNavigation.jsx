const BottomNavigation = () => {
  return (
    <nav
      className="fixed bottom-0 left-0 right-0 z-50 border-t border-orange-200 bg-white"
      style={{ paddingBottom: "env(safe-area-inset-bottom)" }}
    >
      <div className="mx-auto flex max-w-6xl justify-around py-4">
        <span>Home</span>
        <span>Events</span>
        <span>Discussions</span>
        <span>Organizations</span>
        <span>Profile</span>
      </div>
    </nav>
  );
};

export default BottomNavigation;