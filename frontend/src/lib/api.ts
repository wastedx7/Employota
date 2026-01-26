import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  email: string;
  message: string;
  error?: boolean;
}

export interface UserInfo {
  principal: {
    username: string;
  };
  authorities: string[];
  authenticated: boolean;
}

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  role: 'ADMIN' | 'EMPLOYEE';
  createdAt: string;
  updatedAt: string;
}

export interface EmployeeCreateRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: 'EMPLOYEE' | 'ADMIN';
}

export interface EmployeeUpdateRequest {
  firstName?: string;
  lastName?: string;
  email?: string;
  role?: 'EMPLOYEE' | 'ADMIN';
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

// Auth endpoints
export const authApi = {
  login: (data: LoginRequest) => 
    api.post<LoginResponse>('/login', data),
  
  logout: () => 
    api.post('/logout'),
  
  getMe: () => 
    api.get<UserInfo>('/me'),
  
  register: (data: RegisterRequest) =>
    api.post<Employee>('/register', data),
};

// Admin endpoints
export const adminApi = {
  getEmployees: () => 
    api.get<Employee[]>('/admin/employees'),
  
  getEmployee: (id: number) => 
    api.get<Employee>(`/admin/employees/${id}`),
  
  createEmployee: (data: EmployeeCreateRequest) => 
    api.post<Employee>('/admin/employees', data),
  
  updateEmployee: (id: number, data: EmployeeUpdateRequest) => 
    api.patch<Employee>(`/admin/employees/${id}`, data),
  
  deleteEmployee: (id: number) => 
    api.delete(`/admin/employees/${id}`),
};

export default api;
