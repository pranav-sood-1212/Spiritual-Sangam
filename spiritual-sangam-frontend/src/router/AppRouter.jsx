import { Routes, Route } from "react-router-dom";
import AppLayout from "../components/layout/AppLayout";
import LandingPage from "../pages/LandingPage";
import RegisterPage from "../pages/RegisterPage";
import LoginPage from "../pages/LoginPage";
import HomePage from "../pages/HomePage";
import ProtectedRoute from "../components/auth/ProtectedRoute";
import CommunityDetailsPage from "../pages/CommunityDetailsPage";
import EventsPage from "../pages/EventsPage";
import EventDetailsPage from "../pages/EventDetailsPage";
import CommunitiesPage from "../pages/CommunitiesPage";

const Placeholder = ({ title }) => (
  <div className="min-h-screen flex items-center justify-center">
    <h1 className="text-3xl font-semibold">{title}</h1>
  </div>
);

const AppRouter = () => {
  return (
    <Routes>
      <Route element={<AppLayout />}>
        <Route path="/" element={<LandingPage />} />
        <Route path="/home" element={
          <ProtectedRoute>
            <HomePage />
          </ProtectedRoute>
        } />
        <Route path="/communities" element={<CommunitiesPage />} />
        <Route path="/community/:communityId" element={<CommunityDetailsPage />} />
        <Route path="/events" element={<EventsPage />} />
        <Route path="/event/:eventId" element={<EventDetailsPage />} />
        <Route
          path="/organizations"
          element={<Placeholder title="Organizations" />}
        />
        <Route
          path="/discussions"
          element={<Placeholder title="Discussions" />}
        />
        <Route path="/profile" element={<Placeholder title="Profile" />} />

        <Route path="*" element={<Placeholder title="404 - Page Not Found" />} />
      </Route>
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  );
};

export default AppRouter;









//variable 

// const variable=({ ncijewnferfn })=>(


// );


//functions
// const func = ({ vjfdvi })=>{
//   return (

//   );
// };