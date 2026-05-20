import React from "react";
import { motion } from "framer-motion";

const MetricCard = ({
  title = "Metric",
  value = 0,
  icon,
  color = "from-cyan-500 to-blue-600",
  status,
}) => {
  const Icon = icon;

  return (
    <motion.div
      whileHover={{ scale: 1.03 }}
      className="bg-[#111827]/60 backdrop-blur-lg border border-gray-700 rounded-2xl p-6 shadow-xl"
    >
      <div className="flex items-center justify-between">
        <div>
          <p className="text-gray-400 text-sm">{title}</p>
          <h2 className="text-3xl font-bold text-white mt-2">
            {typeof value === "number" ? value.toFixed(1) : (value || 0)}
          </h2>

          {status && (
            <span className="text-xs text-green-400 mt-2 inline-block">
              {status}
            </span>
          )}
        </div>

        <div
          className={`p-4 rounded-xl bg-gradient-to-br ${color}`}
        >
          {Icon && <Icon className="w-8 h-8 text-white" />}
        </div>
      </div>
    </motion.div>
  );
};

export default MetricCard;
