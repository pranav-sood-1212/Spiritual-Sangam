import AuthLayout from "../components/layout/AuthLayout";
import RegisterForm from "../components/auth/RegisterForm";

const RegisterPage = () => {
  return (
    <AuthLayout
      title="Create your account"
      subtitle="Join Spiritual Sangam and become part of a growing spiritual community."
    >
      <RegisterForm />
    </AuthLayout>
  );
};

export default RegisterPage;