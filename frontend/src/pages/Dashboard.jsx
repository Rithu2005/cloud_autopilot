import React, { useState, useEffect } from 'react';
import { Globe, Cpu, Database, HardDrive, Zap, Bell, ShieldAlert } from 'lucide-react';
import { motion } from 'framer-motion';
import { fetchMonitoringStats, fetchIncidents } from '../services/api';
import MetricCard from '../components/MetricCard';
import LoadChart from '../charts/LoadChart';

const Dashboard = () => {
  const [stats, setStats] = useState(null);
  const [incidents, setIncidents] = useState([]);
  const [history, setHistory] = useState([]);

  useEffect(() => {
    const poll = async () => {
      try {
        const [s, i] = await Promise.all([fetchMonitoringStats(), fetchIncidents()]);
        setStats(s);
        setIncidents(i || []);
        
        const newTime = new Date().toLocaleTimeString();
        const newCpu = s?.cpuUsage || 0;
        
        setHistory(prev => {
          const updated = [...prev, { time: newTime, cpu: newCpu }];
          return updated.slice(-10);
        });
      } catch (e) { 
        console.error("Polling error:", e); 
      }
    };
    poll();
    const t = setInterval(poll, 5000);
    return () => clearInterval(t);
  }, []);

  return (
    <div className="min-h-screen p-6 text-white bg-[#050816]">
      {/* Navigation */}
      <nav className="flex justify-between items-center mb-10 bg-[#111827]/70 border border-white/10 p-4 rounded-2xl backdrop-blur-md">
        <div className="flex items-center gap-3">
          <Globe className="text-blue-500" size={32} />
          <h1 className="text-2xl font-bold bg-gradient-to-r from-blue-400 to-pink-500 bg-clip-text text-transparent">
            Cloud AutoPilot X
          </h1>
        </div>
        <div className="flex gap-6 items-center">
          <div className="flex items-center gap-2 text-sm text-gray-400">
            <span className="w-2 h-2 bg-green-500 rounded-full animate-pulse" /> 
            Gateway Live
          </div>
          <Bell className="cursor-pointer hover:text-blue-400 transition-colors" />
        </div>
      </nav>

      {/* Hero Section */}
      <section className="mb-10 text-center">
        <motion.h2 
          initial={{ opacity: 0, y: 20 }} 
          animate={{ opacity: 1, y: 0 }} 
          className="text-4xl font-extrabold mb-4"
        >
          Autonomous Cloud SRE Monitoring Platform
        </motion.h2>
        <p className="text-gray-400">
          Real-time observability, incident tracking, and intelligent infrastructure monitoring.
        </p>
      </section>

      {/* Metrics Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
        <MetricCard 
          title="CPU Usage" 
          value={stats?.cpuUsage || 0} 
          icon={Cpu} 
          color="from-red-500 to-orange-600" 
          status="Active" 
        />
        <MetricCard 
          title="Memory" 
          value={stats?.memoryUsage || 0} 
          icon={Database} 
          color="from-pink-500 to-rose-600" 
          status="Optimized" 
        />
        <MetricCard 
          title="Disk Load" 
          value={stats?.diskUsage || 0} 
          icon={HardDrive} 
          color="from-yellow-500 to-amber-600" 
          status="Stable" 
        />
        <MetricCard 
          title="Active Incidents" 
          value={incidents?.length || 0} 
          icon={Zap} 
          color="from-blue-500 to-indigo-600" 
          status="Monitored" 
        />
      </div>

      {/* Charts & Insights */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2">
          <LoadChart data={history} />
        </div>
        <div className="bg-[#111827]/70 p-6 rounded-2xl border border-white/10 relative overflow-hidden backdrop-blur-md">
          <h3 className="text-xl font-semibold mb-6 flex items-center gap-2">
            <ShieldAlert className="text-amber-500" /> AI Insight
          </h3>
          <p className="text-gray-400 leading-relaxed">
            {stats ? `System status is ${stats.status || 'Unknown'}. ` : "Connecting to system..."}
            <br/><br/> 
            Intelligence engine reports infrastructure scaling remains efficient and no immediate threats detected.
          </p>
          <div className="absolute -bottom-10 -right-10 w-40 h-40 bg-blue-500/10 rounded-full blur-3xl"></div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
