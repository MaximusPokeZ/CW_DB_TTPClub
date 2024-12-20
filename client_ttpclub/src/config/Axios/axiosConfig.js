import axios from "axios";

const setupAxiosInstance = (onSessionExpired) => {
    const axiosInstance = axios.create({
        baseURL: "http://localhost:8080/api/v1",
        headers: {
            "Content-Type": "application/json",
        },
    });

    axiosInstance.interceptors.request.use(
        async (config) => {
            const accessToken = localStorage.getItem("accessToken");
            if (accessToken) {
                config.headers.Authorization = `Bearer ${accessToken}`;
            }
            console.log(config);
            return config;
        },
        (error) => Promise.reject(error)
    );

    axiosInstance.interceptors.response.use(
        (response) => response,
        async (error) => {
            const originalRequest = error.config;

            if (error.response?.status === 401 && !originalRequest._retry) {
                originalRequest._retry = true;
                const refreshToken = localStorage.getItem("refreshToken");

                if (refreshToken) {
                    try {
                        const response = await axios.post(
                            "http://localhost:8080/api/v1/auth/refresh",
                            { refreshToken }
                        );
                        const newAccessToken = response.data;

                        localStorage.setItem("accessToken", newAccessToken);

                        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
                        return axiosInstance(originalRequest);
                    } catch (refreshError) {
                        console.error("Ошибка обновления токена", refreshError);
                        if (onSessionExpired) onSessionExpired();
                    }
                }
            }

            return Promise.reject(error);
        }
    );

    return axiosInstance;
};

export const axiosInstance = setupAxiosInstance();

export default setupAxiosInstance;
