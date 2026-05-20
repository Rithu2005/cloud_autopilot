import React from 'react';

export const IncidentTable = ({ incidents, error }) => (
  <div className="glass-card table-section">
    <h3>Active Incidents</h3>
    {error ? (
      <p style={{ color: '#ef4444' }}>Incident Service Unreachable.</p>
    ) : incidents.length === 0 ? (
      <p>No Active Incidents</p>
    ) : (
      <table>
        <thead><tr><th>Title</th><th>Severity</th><th>Status</th></tr></thead>
        <tbody>{incidents.map(i => <tr key={i.id}><td>{i.title}</td><td>{i.severity}</td><td>{i.status}</td></tr>)}</tbody>
      </table>
    )}
  </div>
);
