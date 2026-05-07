const API = "http://localhost:8081";

// --- TOAST SYSTEM ---
function showToast(message, type = 'success') {
    let container = document.getElementById('toastContainer');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toastContainer';
        document.body.appendChild(container);
    }
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    
    // Icon logic
    const icon = type === 'success' ? '✅' : type === 'error' ? '⚠️' : 'ℹ️';
    
    toast.innerHTML = `<span>${icon}</span> <span>${message}</span>`;
    container.appendChild(toast);
    setTimeout(() => toast.remove(), 4500);
}

// --- AUTH CHECK ---
function requireAuth() {
    const user = sessionStorage.getItem('user');
    if (!user) {
        window.location.href = '/login.html';
        return null;
    }
    return JSON.parse(user);
}

// --- GENERAL NAVIGATION ---
function logout() {
    sessionStorage.removeItem('user');
    showToast('Logged out securely.', 'success');
    window.location.href = '/login.html';
}
