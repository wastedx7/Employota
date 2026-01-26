import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider } from "@/contexts/AuthContext";
import { ProtectedRoute } from "@/components/ProtectedRoute";
import { AdminLayout } from "@/components/layouts/AdminLayout";
import { EmployeeLayout } from "@/components/layouts/EmployeeLayout";
import { Login } from "@/pages/Login";
import { Register } from "@/pages/Register";
import { AdminHome } from "@/pages/admin/AdminHome";
import { EmployeeList } from "@/pages/admin/EmployeeList";
import { EmployeeCreate } from "@/pages/admin/EmployeeCreate";
import { EmployeeEdit } from "@/pages/admin/EmployeeEdit";
import { EmployeeHome } from "@/pages/employee/EmployeeHome";
import NotFound from "./pages/NotFound";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <AuthProvider>
        <Toaster />
        <Sonner />
        <BrowserRouter>
          <Routes>
            {/* Public routes */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            
            {/* Redirect root to login */}
            <Route path="/" element={<Navigate to="/login" replace />} />
            
            {/* Admin routes */}
            <Route
              path="/admin"
              element={
                <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                  <AdminLayout />
                </ProtectedRoute>
              }
            >
              <Route index element={<Navigate to="/admin/home" replace />} />
              <Route path="home" element={<AdminHome />} />
              <Route path="employees" element={<EmployeeList />} />
              <Route path="employees/new" element={<EmployeeCreate />} />
              <Route path="employees/edit/:id" element={<EmployeeEdit />} />
            </Route>
            
            {/* Employee routes */}
            <Route
              path="/employee"
              element={
                <ProtectedRoute allowedRoles={['ROLE_EMPLOYEE']}>
                  <EmployeeLayout />
                </ProtectedRoute>
              }
            >
              <Route index element={<Navigate to="/employee/home" replace />} />
              <Route path="home" element={<EmployeeHome />} />
            </Route>
            
            {/* Catch-all */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
