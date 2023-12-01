FROM jruby:9.4-jre17
# FROM ruby:3.1-alpine

# throw errors if Gemfile has been modified since Gemfile.lock
RUN bundle config --global frozen 1

WORKDIR /usr/src/app

COPY Gemfile  ./
COPY Gemfile.lock ./
COPY advent23.gemspec ./

RUN bundle lock --add-platform universal-java-17
RUN bundle install
RUN bundle binstubs --all

COPY . .
RUN export PATH=$PATH:/usr/src/app/bin

CMD ["rake"]