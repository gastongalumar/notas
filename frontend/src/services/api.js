// src/services/api.js
const API_BASE = '/api/notas';

const fetchAPI = async (url, options = {}) => {
    const response = await fetch(url, {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...options.headers
        }
    });

    if (!response.ok) {
        // ❌ ESTABA MAL: throw new Error(HTTP error! status: ${response.status});
        // ✅ CORRECTO: usar backticks
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return response.json();
};

export const noteService = {
    getActiveNotes: () => fetchAPI(`${API_BASE}/activas`),
    getArchivedNotes: () => fetchAPI(`${API_BASE}/archivadas`),
    createNote: (noteData) => fetchAPI(API_BASE, {
        method: 'POST',
        body: JSON.stringify(noteData)
    }),
    archiveNote: (id) => fetchAPI(`${API_BASE}/${id}/archivar`, {
        method: 'PATCH'
    }),
    unarchiveNote: (id) => fetchAPI(`${API_BASE}/${id}/desarchivar`, {
        method: 'PATCH'
    }),
    deleteNote: (id) => fetchAPI(`${API_BASE}/${id}`, {
        method: 'DELETE'
    })
};