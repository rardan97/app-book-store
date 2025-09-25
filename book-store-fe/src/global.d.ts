
declare global {
  interface Window {
    RUNTIME_CONFIG?: {
      VITE_REST_API_BASE_URL_USER: string;
      VITE_REST_API_BASE_URL_USER_AUTH: string;
      VITE_REST_API_BASE_URL_USER_PUBLIC: string;

      VITE_REST_API_BASE_URL_STAFF?: string;
      VITE_REST_API_BASE_URL_STAFF_AUTH?: string;
    };
  }
}

export {};
