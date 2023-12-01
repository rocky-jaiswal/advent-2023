require_relative "../lib/advent23"

task :hello do
  Advent23::App.new.main
end

task :main do
  Advent23::App.new.day1
end