resource "aws_cloudwatch_log_group" "autopilot_logs" {
  name              = "/aws/autopilot/logs"
  retention_in_days = 7
  tags              = { Name = "${var.project_name}-logs" }
}
