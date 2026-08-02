import AuthLayout from "../components/layout/AuthLayout";
import LoginForm from "../components/auth/LoginForm";

const LoginPage = () => {
  return (
    <AuthLayout
      title="Welcome Back"
      subtitle="Sign in to continue your spiritual journey."
    >
      <LoginForm />
    </AuthLayout>
  );
};

export default LoginPage;