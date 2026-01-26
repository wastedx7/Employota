import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from '@/contexts/AuthContext';
import { Loader2 } from 'lucide-react';

interface ProtectedRouteProps {
  children: React.ReactNode;
  allowedRoles?: string[];
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ 
  children, 
  allowedRoles = [] 
}) => {
  const { isAuthenticated, isLoading, user } = useAuth();
  const location = useLocation();

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-background">
        <Loader2 className="h-8 w-8 animate-spin text-primary" />
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  if (allowedRoles.length > 0) {
    const hasRequiredRole = allowedRoles.some(role => 
      user?.authorities?.includes(role)
    );
    
    if (!hasRequiredRole) {
      // Redirect to appropriate dashboard based on role
      if (user?.authorities?.includes('ROLE_ADMIN')) {
        return <Navigate to="/admin/home" replace />;
      }
      if (user?.authorities?.includes('ROLE_EMPLOYEE')) {
        return <Navigate to="/employee/home" replace />;
      }
      return <Navigate to="/login" replace />;
    }
  }

  return <>{children}</>;
};
