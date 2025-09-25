interface RuntimeConfig {
  VITE_REST_API_BASE_URL_USER?: string;
  VITE_REST_API_BASE_URL_USER_AUTH?: string;
  VITE_REST_API_BASE_URL_USER_PUBLIC?: string;

  VITE_REST_API_BASE_URL_STAFF?: string;
  VITE_REST_API_BASE_URL_STAFF_AUTH?: string;

  VITE_MIDTRANS_CLIENT_KEY?: string;
}

// const runtimeConfig = window.RUNTIME_CONFIG
const runtimeConfig: RuntimeConfig = window.RUNTIME_CONFIG ?? {};

export const REST_API_BASE_URL_USER = runtimeConfig.VITE_REST_API_BASE_URL_USER;
export const REST_API_BASE_URL_USER_AUTH = runtimeConfig.VITE_REST_API_BASE_URL_USER_AUTH;
export const REST_API_BASE_URL_USER_PUBLIC = runtimeConfig.VITE_REST_API_BASE_URL_USER_PUBLIC;

export const REST_API_BASE_URL_STAFF = runtimeConfig.VITE_REST_API_BASE_URL_STAFF;
export const REST_API_BASE_URL_STAFF_AUTH = runtimeConfig.VITE_REST_API_BASE_URL_STAFF_AUTH;


export const VITE_MIDTRANS_CLIENT_KEY = runtimeConfig.VITE_MIDTRANS_CLIENT_KEY;