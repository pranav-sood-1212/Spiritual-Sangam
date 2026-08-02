const AuthLayout = ({ title, subtitle, children }) => {
  return (
    <main className="flex min-h-screen items-center justify-center bg-orange-50 px-4 py-10">
      <div className="w-full max-w-md rounded-2xl bg-white p-8 shadow-lg">
        <div className="mb-8 text-center">
          <h1 className="text-3xl font-bold text-gray-900">
            {title}
          </h1>

          <p className="mt-2 text-gray-600">
            {subtitle}
          </p>
        </div>

        {children}
      </div>
    </main>
  );
};

export default AuthLayout;