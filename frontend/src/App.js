import React, { useState, useEffect } from 'react';
import { noteService } from './services/api';
import './App.css';

function App() {
  const [activeNotes, setActiveNotes] = useState([]);
  const [archivedNotes, setArchivedNotes] = useState([]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [activeTab, setActiveTab] = useState('active');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const loadNotes = async () => {
    setLoading(true);
    setError(null);
    try {
      const [active, archived] = await Promise.all([
        noteService.getActiveNotes(),
        noteService.getArchivedNotes()
      ]);
      setActiveNotes(active);
      setArchivedNotes(archived);
    } catch (error) {
      setError('⚠️ No se puede conectar al backend. Ejecuta: ./gradlew bootRun en la carpeta backend/');
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadNotes();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title.trim() || !content.trim()) return;

    try {
      await noteService.createNote({
        titulo: title,
        contenido: content,
        archivada: false
      });
      setTitle('');
      setContent('');
      await loadNotes();
      alert('✅ Nota creada exitosamente!');
    } catch (error) {
      alert('❌ Error al crear nota: ' + error.message);
    }
  };

  const handleArchive = async (id, archive = true) => {
    try {
      if (archive) {
        await noteService.archiveNote(id);
      } else {
        await noteService.unarchiveNote(id);
      }
      await loadNotes();
      alert(archive ? '📁 Nota archivada' : '📝 Nota desarchivada');
    } catch (error) {
      alert('Error cambiando estado');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('¿Estás seguro de eliminar esta nota?')) return;

    try {
      await noteService.deleteNote(id);
      await loadNotes();
      alert('🗑️ Nota eliminada');
    } catch (error) {
      alert('Error eliminando nota');
    }
  };

  const renderNote = (note, isArchived = false) => (
    <div key={note.id} style={{
      border: '1px solid #e0e0e0',
      borderRadius: '8px',
      padding: '16px',
      marginBottom: '12px',
      backgroundColor: '#fff',
      boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
    }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
        <h3 style={{ margin: '0 0 8px 0' }}>{note.titulo}</h3>
        <span style={{
          padding: '4px 8px',
          backgroundColor: isArchived ? '#f0f0f0' : '#e3f2fd',
          color: isArchived ? '#666' : '#1976d2',
          borderRadius: '12px',
          fontSize: '12px',
          fontWeight: 'bold'
        }}>
          {isArchived ? 'ARCHIVADA' : 'ACTIVA'}
        </span>
      </div>
      <p style={{
        margin: '8px 0',
        color: '#555',
        whiteSpace: 'pre-wrap'
      }}>{note.contenido}</p>
      <div style={{ display: 'flex', gap: '8px', marginTop: '12px' }}>
        <button
          onClick={() => handleArchive(note.id, !isArchived)}
          style={{
            padding: '6px 12px',
            backgroundColor: isArchived ? '#2196f3' : '#757575',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '14px'
          }}
        >
          {isArchived ? 'Desarchivar' : 'Archivar'}
        </button>
        <button
          onClick={() => handleDelete(note.id)}
          style={{
            padding: '6px 12px',
            backgroundColor: '#f44336',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer',
            fontSize: '14px'
          }}
        >
          Eliminar
        </button>
      </div>
    </div>
  );

  return (
    <div style={{
      padding: '24px',
      maxWidth: '800px',
      margin: '0 auto',
      fontFamily: 'Arial, sans-serif'
    }}>
      <header style={{
        backgroundColor: '#1976d2',
        color: 'white',
        padding: '20px',
        borderRadius: '8px',
        marginBottom: '24px',
        textAlign: 'center'
      }}>
        <h1 style={{ margin: 0 }}>📚 Gestor de Notas</h1>
        <p style={{ margin: '8px 0 0 0', opacity: 0.9 }}>Prueba Técnica - Spring Boot + React</p>
      </header>

      {error && (
        <div style={{
          backgroundColor: '#ffebee',
          color: '#c62828',
          padding: '12px',
          borderRadius: '4px',
          marginBottom: '20px',
          borderLeft: '4px solid #c62828'
        }}>
          <strong>⚠️ Error:</strong> {error}
        </div>
      )}

      <div style={{
        backgroundColor: 'white',
        padding: '20px',
        borderRadius: '8px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        marginBottom: '24px'
      }}>
        <h2 style={{ marginTop: 0 }}>📝 Crear Nueva Nota</h2>
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '16px' }}>
            <input
              type="text"
              placeholder="Título de la nota"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              style={{
                width: '100%',
                padding: '12px',
                fontSize: '16px',
                border: '1px solid #ddd',
                borderRadius: '4px',
                boxSizing: 'border-box'
              }}
              required
              disabled={loading}
            />
          </div>
          <div style={{ marginBottom: '16px' }}>
            <textarea
              placeholder="Contenido de la nota"
              value={content}
              onChange={(e) => setContent(e.target.value)}
              style={{
                width: '100%',
                padding: '12px',
                fontSize: '16px',
                border: '1px solid #ddd',
                borderRadius: '4px',
                minHeight: '120px',
                boxSizing: 'border-box',
                resize: 'vertical'
              }}
              required
              disabled={loading}
            />
          </div>
          <button
            type="submit"
            disabled={loading}
            style={{
              padding: '12px 24px',
              backgroundColor: '#1976d2',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              fontSize: '16px',
              cursor: loading ? 'not-allowed' : 'pointer',
              opacity: loading ? 0.7 : 1
            }}
          >
            {loading ? 'Creando...' : 'Crear Nota'}
          </button>
        </form>
      </div>

      <div style={{ marginBottom: '24px' }}>
        <div style={{
          display: 'flex',
          borderBottom: '1px solid #ddd',
          marginBottom: '16px'
        }}>
          <button
            onClick={() => setActiveTab('active')}
            style={{
              padding: '12px 24px',
              backgroundColor: activeTab === 'active' ? '#1976d2' : 'transparent',
              color: activeTab === 'active' ? 'white' : '#1976d2',
              border: 'none',
              cursor: 'pointer',
              fontSize: '16px',
              borderTopLeftRadius: '4px',
              borderTopRightRadius: '4px',
              marginRight: '4px'
            }}
          >
            Notas Activas ({activeNotes.length})
          </button>
          <button
            onClick={() => setActiveTab('archived')}
            style={{
              padding: '12px 24px',
              backgroundColor: activeTab === 'archived' ? '#757575' : 'transparent',
              color: activeTab === 'archived' ? 'white' : '#757575',
              border: 'none',
              cursor: 'pointer',
              fontSize: '16px',
              borderTopLeftRadius: '4px',
              borderTopRightRadius: '4px'
            }}
          >
            Notas Archivadas ({archivedNotes.length})
          </button>
        </div>

        {loading ? (
          <div style={{ textAlign: 'center', padding: '40px' }}>
            <p>Cargando notas...</p>
          </div>
        ) : activeTab === 'active' ? (
          activeNotes.length === 0 ? (
            <div style={{ textAlign: 'center', padding: '40px', color: '#777' }}>
              <p>📋 No hay notas activas. ¡Crea tu primera nota!</p>
            </div>
          ) : (
            <div>
              {activeNotes.map(note => renderNote(note, false))}
            </div>
          )
        ) : (
          archivedNotes.length === 0 ? (
            <div style={{ textAlign: 'center', padding: '40px', color: '#777' }}>
              <p>📭 No hay notas archivadas</p>
            </div>
          ) : (
            <div>
              {archivedNotes.map(note => renderNote(note, true))}
            </div>
          )
        )}
      </div>

      <footer style={{
        marginTop: '40px',
        paddingTop: '20px',
        borderTop: '1px solid #ddd',
        textAlign: 'center',
        color: '#666',
        fontSize: '14px'
      }}>
        <p>🚀 Backend: Spring Boot + MySQL | Frontend: React</p>
        <p>✅ CRUD completo + Archivar/Desarchivar notas</p>
      </footer>
    </div>
  );
}

export default App;